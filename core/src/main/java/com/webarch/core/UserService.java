package com.webarch.core;

import com.webarch.model.User;

/**
 * package: com.webarch.core <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:简单用户服务，用户信息CRUD
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/19 19:42
 */
public interface UserService {
    void insertUser(User user);

    void updateUser(User user);
}
