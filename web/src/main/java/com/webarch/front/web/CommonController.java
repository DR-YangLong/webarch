package com.webarch.front.web;

import com.baidu.ueditor.ActionEnter;
import com.webarch.common.constant.ValidateBizRule;
import com.webarch.common.io.img.ValidateImg;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * package: com.webarch.front.web <br/>
 * functional describe:通用控制器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/10 20:20
 */
@Controller
@RequestMapping
public class CommonController {
    private static final Logger logger= LoggerFactory.getLogger(CommonController.class);
    /**
     * 获取验证码图片
     * <code>ruleName</code> is {@link com.webarch.common.constant.ValidateBizRule}
     *
     * @param response 响应对象
     */
    @RequestMapping(value = "/common/validateImg/{ruleName:\\d+}/ajax")
    public void getValiDate(@PathVariable(value = "ruleName") String ruleName, HttpServletResponse response) {
        try {
            //获取业务值的key
            String key = ValidateBizRule.getValidateKey(ruleName);
            if (key == null || "".equals(key)) {
                throw new IOException("没有业务规则！");
            }
            ValidateImg validateImg = new ValidateImg();
            //根据规则和用户唯一信息生成全局唯一凭证放入状态缓存中，此处示例使用session以隔离达成唯一
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(key, validateImg.getCode());
            // 设置响应的类型格式为图片格式
            response.setContentType("image/jpeg");
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            OutputStream outputStream = response.getOutputStream();
            validateImg.write(outputStream);
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            logger.debug("发送登陆验证图片出错！错误信息：" + e.getMessage());
        }
    }

    /**
     * 初始化百度编辑器，可传入其他参数生成不同的编辑器
     * @param response
     * @param request
     */
    @RequestMapping("/ueditor/init")
    public void initUeditor(HttpServletResponse response,HttpServletRequest request){
        response.setContentType("application/json");
        //配置路径，首先还web app跟目录
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        rootPath=rootPath+"static";
        PrintWriter writer=null;
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            writer = response.getWriter();
            writer.write(exec);
            writer.flush();
        } catch (IOException e) {
            logger.error("百度编辑器初始化错误！",e);
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }
}
