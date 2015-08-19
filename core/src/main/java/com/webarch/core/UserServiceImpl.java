package com.webarch.core;

import com.webarch.dao.UserMapper;
import com.webarch.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * package: com.webarch.core <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:用户简单服务接口实现
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/19 19:43
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userDao;

    @Override
    public void insertUser(User user) {
        String pwd=user.getPassword();
        pwd= DigestUtils.md5Hex(pwd);
        user.setPassword(pwd);
        userDao.insertSelective(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }
}
