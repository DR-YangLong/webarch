package com.webarch.core;

import com.webarch.model.User;

/**
 * package: com.webarch.core <br/>
 * functional describe:登录服务
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/10 20:32
 */
public interface LoginService {

    //前置处理，验证用户是否需要验证码，验证码是否正确
    boolean beforeLogin(String principal,String captcha);

    //登录成功处理，记录日志，清除错误次数，验证码状态等
    void loginSuccess(Integer id,String ip);

    //登录失败，记录日志，更新错误次数，验证码状态等
    boolean loginFailure(String principal);

    User getUserById(Integer id);
}
