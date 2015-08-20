package com.webarch.front.web.passport;

import com.alibaba.fastjson.JSON;
import com.webarch.common.web.ViewGenerator;
import com.webarch.core.UserService;
import com.webarch.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * package: com.webarch.front.web.passport <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:注册控制器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/18 19:38
 */
@Controller
public class RegisterController {
    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserService userService;

    /**
     * 使用自动验证数据
     *
     * @param user   用户对象
     * @param errors 验证错误对象
     * @return
     */
    @RequestMapping("/reg")
    public ModelAndView register(@Valid User user, Errors errors) {
        ModelAndView modelAndView = ViewGenerator.getModelAndView();
        if (errors.hasErrors()) {//验证出错
            modelAndView.setViewName("/front/passport/regIndex");
        } else {
            try {
                userService.insertUser(user);
                modelAndView.addObject("User", user);
                //TODO 自动登录
            } catch (Exception e) {
                log.error("insert user to db error!obj:" + JSON.toJSONString(user), e);
                modelAndView.setViewName("/front/passport/regIndex");
            }
        }
        return modelAndView;
    }
}
