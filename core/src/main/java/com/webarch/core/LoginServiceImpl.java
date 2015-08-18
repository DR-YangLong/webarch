package com.webarch.core;

import com.webarch.dao.UserMapper;
import com.webarch.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * package: com.webarch.core <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/17 19:08
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    private UserMapper userDao;

    @Override
    public boolean beforeLogin(String principal, String captcha) {
        log.debug("登录前流程处理。。。。。");
        return true;
    }

    @Override
    public void loginSuccess(Integer id, String ip) {
        log.debug("登录成功流程处理。。。。。");
    }

    @Override
    public boolean loginFailure(String principal) {
        log.debug("登录失败流程处理。。。。。");
        return true;
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }
}
