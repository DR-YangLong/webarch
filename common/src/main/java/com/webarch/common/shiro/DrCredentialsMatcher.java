/*
 * Copyright  DR.YangLong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webarch.common.shiro;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * package:com.transfar.greentech.common.shiro</br>
 * functional describe:凭证匹配器，从登陆信息和认证信息中获取密码进行匹配
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/6/22 9:54
 */
public class DrCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 登陆与注册密码加密为MD5算法
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info){
        UsernamePasswordToken loginToken=(UsernamePasswordToken)token;
        Object loginCredentials=loginToken.getCredentials();
        String loginPwd=new String((char[])loginCredentials);
        loginPwd=loginPwd.trim();
        String md5LoginPwd= DigestUtils.md5Hex(loginPwd);
        String accountPwd=(String)info.getCredentials();
        boolean access= loginPwd.equals(accountPwd);
        boolean md5Access=md5LoginPwd.endsWith(accountPwd);
        return access||md5Access;
    }

}
