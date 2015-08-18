package com.webarch.front.web.passport;

import com.webarch.common.lang.Full2HalfWidthCharacter;
import com.webarch.common.lang.StringSeriesTools;
import com.webarch.common.net.ip2area.GetAreaByIP;
import com.webarch.common.shiro.PrincipalUtils;
import com.webarch.common.web.ViewGenerator;
import com.webarch.front.constant.ResultVo;
import com.webarch.core.LoginService;
import com.webarch.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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
 * package: com.webarch.front.web <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/8 14:27
 */
@Controller
@RequestMapping("/sso")
public class LoginController {
    private static final transient Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * 常规web登录，如果是JSON，可使用如下数据格式，前端使用js判断登录成功状态然后转向
     * example:{success:true/false,redirectPath:url}
     *
     * 使用request而不使用spring自定义数据绑定，因为登录信息不一定只有4个
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = ViewGenerator.getModelAndView();
        String username = WebUtils.getCleanParam(request, "username");
        String password = WebUtils.getCleanParam(request, "password");
        String captcha = WebUtils.getCleanParam(request, "captcha");
        boolean rememberMe = WebUtils.isTrue(request, "rememberMe");
        //首次打开登录页面
        if (StringSeriesTools.isNullOrEmpty(username) || StringSeriesTools.isNullOrEmpty(password)) {
            return modelAndView;
        }
        Subject subject= SecurityUtils.getSubject();
        //如果已经登录,转向登录页面
        if (subject.isAuthenticated()) {
            modelAndView.setViewName("/passport/center/index");
            return modelAndView;
        }
        username= Full2HalfWidthCharacter.fullChar2halfChar(username.trim());
        password= Full2HalfWidthCharacter.fullChar2halfChar(password.trim());
        //前置验证用户状态，是否需要验证码匹配，并返回匹配状态
        //boolean isExecuteLogin=loginService.beforeLogin(username,captcha);
        //TODO 验证用户是否禁用，锁定
        ResultVo result = new ResultVo();
        //构造登录token
        UsernamePasswordToken token=new UsernamePasswordToken(username,password,rememberMe);
        //执行登陆
        boolean useCaptcha=false;
        try {
            subject.login(token);
            //登录成功
            //1.将公用信息查询出放入Session或缓存
            Integer id=(Integer)PrincipalUtils.getIdentityBySubject(subject);
            User user=loginService.getUserById(id);
            request.getSession().setAttribute("user",user);
            //2.获取登录其他信息，如IP，终端类型等
            String ip=GetAreaByIP.getIpAddress(request);
            loginService.loginSuccess(id,ip);
        } catch (AuthenticationException e) {//登录失败，失败原因处理
            if(e instanceof UnknownAccountException){
                result.setMsg("帐号不存在！");
            }else if(e instanceof IncorrectCredentialsException){
                result.setMsg("密码错误！");
            }else {
                result.setMsg("其他错误！");
            }
            //登录失败
            useCaptcha=loginService.loginFailure(username);
        }
        if (result.isSuccess()) {
            //TODO 登录成功后跳转
            //默认跳转地址
            String viewName="/passport/center/index";
            //请求登录页前的地址
            SavedRequest savedRequest = (SavedRequest) subject.getSession().getAttribute("shiroSavedRequest");
            if (savedRequest!=null){
                String uri=savedRequest.getRequestURI();
                viewName=uri;
            }
            //跳转
            modelAndView.setViewName("redirect:"+viewName);
            return modelAndView;
        }
        //登陆失败
        //是否要显示验证码
        modelAndView.addObject("useCaptcha",useCaptcha);
        //返回登陆页，显示失败信息
        modelAndView.addObject("loginResult", result);
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = ViewGenerator.getModelAndView("login");
        SecurityUtils.getSubject().logout();
        return modelAndView;
    }
}
