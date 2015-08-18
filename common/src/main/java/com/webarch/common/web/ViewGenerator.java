/*
        Copyright  DR.YangLong

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package com.webarch.common.web;

import org.springframework.web.servlet.ModelAndView;

/**
 * 根据控制器上下文自动生成ModelAndView
 * 全限定目录命名方式：系统前缀（org.apache）.PROJECT_NAME.web.MODULE_NAME.xxController
 *
 * @author yuyi
 * @version 1.0
 * @Modify by DR.YangLong since
 */
public class ViewGenerator {

    private ModelAndView mav;//需生成的mav对象

    public ViewGenerator(ViewGeneratorConfig config) {
        this.mav = new ModelAndView(getViewString(config));
    }

    /**
     * 获取视图名称
     *
     * @param config 配置项
     * @return 视图名称
     */
    private String getViewString(ViewGeneratorConfig config) {
        config = config == null ? new ViewGeneratorConfig() : config;
        String first_root = turnToPath(config.getView_root_name());
        String project_name = config.getProject_name();
        project_name = project_name == null ? generateProjectName(getCaller()) : turnToPath(project_name);
        String module_name = config.getModule_name();
        module_name = module_name == null ? generateModuleName(getCaller()) : turnToPath(module_name);
        String view_name = config.getView_file_name();
        view_name = view_name == null ? generateViewName(getCaller()) : view_name;
        view_name = first_root + project_name + module_name + view_name;
        return view_name;
    }

    /**
     * 路径拼接
     * @param str 文件夹名称
     * @return 路径
     */
    private String turnToPath(String str) {
        str = str == null ? "" : str;
        if ("".equals(str)) {
            return str;
        }
        return str.contains("/") ? str : str + "/";
    }

    /**
     * 生成模块路径
     * 类的路径规则：web.[模块路径].XXController
     * 全限定系统前缀（org.apache）.PROJECT_NAME.web.MODULE_NAME.xxController
     *
     * @param caller StackTraceElement
     * @return 模块名
     */
    private String generateModuleName(StackTraceElement caller) {
        String[] nameStr = caller.getClassName().split("\\.");
        String modulePath = "";
        for (int i = nameStr.length - 2; i > 0; i--) {
            if ("web".equals(nameStr[i])) {
                modulePath = nameStr[i + 1] + "/";
            }
        }
        return modulePath;
    }

    /**
     * 生成工程名称
     * 规则：系统前缀.[工程名]...
     * 全限定 系统前缀（org.apache）.PROJECT_NAME.web.MODULE_NAME.xxController
     *
     * @param caller StackTraceElement
     * @return 获取项目名称
     */
    private String generateProjectName(StackTraceElement caller) {
        String projectPath = "";
        String[] nameStr = caller.getClassName().split("\\.");
        for (int i = nameStr.length - 2; i > 0; i--) {
            if ("web".equals(nameStr[i])) {
                projectPath = nameStr[i - 1] + "/";
            }
        }
        return projectPath;
    }

    /**
     * 生成视图文件名称
     * 规则：调用者方法名
     *
     * @param caller StackTraceElement
     * @return 获取调用的方法名
     */
    private String generateViewName(StackTraceElement caller) {
        return caller.getMethodName();
    }

    /**
     * 获取调用者
     *
     * @return StackTraceElement
     */
    private StackTraceElement getCaller() {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        boolean flag = false;
        StackTraceElement caller = null;
        for (StackTraceElement ste : stack) {
            if (flag) {
                caller = ste;
                break;
            }
            if (ste.getClassName().equals(this.getClass().getName()) && ste.getMethodName().equals("getModelAndView")) {
                flag = true;
            }
        }
        return caller;
    }

    /**
     * 自动生成视图，视图文件名与Controller方法一致
     *
     * @return 视图
     */
    public static ModelAndView getModelAndView() {
        ViewGenerator generator = new ViewGenerator(null);
        return generator.mav;
    }

    /**
     * 只传入视图文件名称，生成视图
     *
     * @param fileName 视图文件名，不带后缀 eg:view.jsp===>view
     * @return 视图
     */
    public static ModelAndView getModelAndView(String fileName) {
        ViewGeneratorConfig config = new ViewGeneratorConfig();
        config.setView_file_name(fileName);
        ViewGenerator generator = new ViewGenerator(config);
        return generator.mav;
    }

    /**
     * 使用配置方式生成视图名称,拼接各个属性值字符串为路径，
     * view_root_name/project_name/module_name/view_file_name
     * 注意使用多级时，view_file_name之前的路径不可以使用“/”，只有view_file_name中才可附带"/"
     * eg:
     * 有一个视图不再自动生成对应目录下，可以使用
     * new ViewGeneratorConfig("","","","/mypath/define/real/fileName")
     * ==》 /default/mypath/define/ral/fileName.ftl
     * 也可以使用
     * new ViewGeneratorConfig("mypath","define","real","fileName")
     * ==》 /default/mypath/define/ral/fileName.ftl
     *
     * @param config 配置项
     * @return 视图
     */
    public static ModelAndView getModelAndView(ViewGeneratorConfig config) {
        ViewGenerator generator = new ViewGenerator(config);
        return generator.mav;
    }

}
