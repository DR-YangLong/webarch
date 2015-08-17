package com.webarch.web;

import com.webarch.common.lang.Full2HalfWidthCharacter;
import com.webarch.common.lang.StringSeriesTools;
import com.webarch.common.web.ViewGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * package: com.webarch.web <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/7 14:27
 */
@Controller
@RequestMapping("/sso")
public class LoginController {
    private static final transient Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = ViewGenerator.getModelAndView();
        String username = WebUtils.getCleanParam(request, "username");
        String password = WebUtils.getCleanParam(request, "password");
        String captcha = WebUtils.getCleanParam(request, "captcha");
        boolean rememberMe = WebUtils.isTrue(request, "rememberMe");
        //首次打开
        if (StringSeriesTools.isNullOrEmpty(username) || StringSeriesTools.isNullOrEmpty(password)) {
            return modelAndView;
        }
        Subject subject= SecurityUtils.getSubject();
        //如果已经登录
        if (subject.isAuthenticated()) {
            modelAndView.setViewName("/passport/center/index");
            return modelAndView;
        }
        username= Full2HalfWidthCharacter.fullChar2halfChar(username.trim());
        password= Full2HalfWidthCharacter.fullChar2halfChar(password.trim());
        ResultVo result = loginService.loginHandler(username, password, rememberMe, captcha, request);
        if (result.isSuccess()) {//登陆成功，跳转
            //TODO
            String viewName="/passport/center/index";
            SavedRequest savedRequest = (SavedRequest) subject.getSession().getAttribute("shiroSavedRequest");
            if (savedRequest!=null){
                String uri=savedRequest.getRequestURI();
                viewName=uri;
            }
            modelAndView.setViewName("redirect:"+viewName);
            return modelAndView;
        }
        //登陆失败，返回登陆页，显示失败信息
        modelAndView.addObject("loginResult", result);
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = ViewGenerator.getModelAndView("lgoin");
        SecurityUtils.getSubject().logout();
        return modelAndView;
    }
}
