/*
 * Copyright (c) 2015 传化科技服务有限公司(Transfar Group) All rights reserved
 */

package com.webarch.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * package: com.transfar.greentech.common.shiro </br>
 * functional describe:shiro获取身份标识的工具
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/6/29 9:06
 */
public class PrincipalUtils {
    /**
     * 获取身份标识，总是获取第一个realm中的标识
     * @return Object
     */
    public static Object getIdentitySingleRealm(){
        Subject subject=SecurityUtils.getSubject();
        return getIdentityBySubject(subject);
    }

    /**
     * 获取身份标识
     * @param subject
     * @return
     */
    public static Object getIdentityBySubject(Subject subject){
        String realmName= subject.getPrincipals().getRealmNames().iterator().next();
        Object identity=subject.getPrincipals().fromRealm(realmName).iterator().next();
        return identity;
    }
}
