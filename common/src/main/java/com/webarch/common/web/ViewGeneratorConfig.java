/*
 * Copyright (c) 2015 传化科技服务有限公司(Transfar Group) All rights reserved
 */

package com.webarch.common.web;

/**
 * package:com.transfar.greentech.common.web
 * functional describe:视图配置文件，视图存放目录如下(如使用FreeMarker,Velocity等模板引擎需考虑引擎配置):
 * +WEB-RROT
 * ---+VIEW_ROOT_NAME
 * -----+PROJECT_NAME
 * -------+MODULE_NAME
 * ---------+VIEW_FILE
 * 对应视图控制器：系统前缀（org.apache）.PROJECT_NAME.web.MODULE_NAME.xxController
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/5/19 9:44
 */
public class ViewGeneratorConfig {
    private static final String DEFAULT_VIEW_ROOT_NAME = "default";
    private String view_root_name = DEFAULT_VIEW_ROOT_NAME;//模板放在web root下视图文件存放位置的第一个路径名
    private String project_name;//当前项目的名称
    private String module_name;//当前模块名称
    private String view_file_name;

    public ViewGeneratorConfig() {
    }

    public ViewGeneratorConfig(String view_root_name, String project_name, String module_name) {
        this.view_root_name = view_root_name;
        this.project_name = project_name;
        this.module_name = module_name;
    }


    public ViewGeneratorConfig(String view_root_name, String project_name, String module_name, String view_file_name) {
        this.view_root_name = view_root_name;
        this.project_name = project_name;
        this.module_name = module_name;
        this.view_file_name = view_file_name;
    }

    public String getView_root_name() {
        return view_root_name;
    }

    public void setView_root_name(String view_root_name) {
        this.view_root_name = view_root_name;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getView_file_name() {
        return view_file_name;
    }

    public void setView_file_name(String view_file_name) {
        this.view_file_name = view_file_name;
    }
}
