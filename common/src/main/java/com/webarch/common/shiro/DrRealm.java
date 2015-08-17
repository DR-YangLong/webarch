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

import com.webarch.common.shiro.exception.AccountLockedException;
import com.webarch.common.shiro.exception.AccountForbiddenException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.Map;

/**
 * package:com.transfar.greentech.common.shiro</br>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/6/22 9:49
 */
public class DrRealm extends AuthorizingRealm{
    /**
     * default value
     */
    private static final String DEFAULT_PWD_KEY = "pwd";
    private static final String DEFAULT_IDENTITY_KEY = "id";
    private static final String DEFAULT_USER_STATUS_KEY = "status";
    private static final String DEFAULT_USER_FORBIDDEN = "1";
    private static final String DEFAULT_USER_LOCKED = "2";
    private static final String DEFAULT_ROLES_KEY = "roles";
    private static final String DEFAULT_PERMS_KEY = "perms";
    private static final String DEFAULT_AUTHEN_FILTER="authc";

    /**
     * 是否使用权限
     */
    private static boolean enablePerms = false;
    /**
     * 是否使用角色
     */
    private static boolean enableRoles = true;
    /**
     * 用户身份唯一标识在Map中的key
     */
    private static String identity_in_map_key = DEFAULT_IDENTITY_KEY;
    /**
     * 密码在map中的key
     */
    private static String password_in_map_key = DEFAULT_PWD_KEY;
    /**
     * 用户状态在map中的key
     */
    private static String user_status_in_map_key = DEFAULT_USER_STATUS_KEY;
    /**
     * 账号禁用时匹配的字符串的值
     */
    private static String user_status_forbidden = DEFAULT_USER_FORBIDDEN;
    /**
     * 账号锁定时匹配的字符串的值
     */
    private static String user_status_locked = DEFAULT_USER_LOCKED;
    /**
     * 角色在map中的KEY
     */
    private static String roles_in_map_key = DEFAULT_ROLES_KEY;
    /**
     * 权限在map中的KEY
     */
    private static String perms_in_map_key = DEFAULT_PERMS_KEY;
    /**
     * 权限过滤器名称
     */
    private static String authenticationFiltername=DEFAULT_AUTHEN_FILTER;
    /**
     * 获取认证和授权信息的服务接口
     */
    private RealmDao realmDao;

    /**
     * 获取认证信息，会通过realmDao或取用户的id和密码【此2项是必须的】，以及用户状态【非必须，当获取为NULL时不会中断认证】
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<String, Object> info = realmDao.getUserUniqueIdentityAndPassword(token.getUsername());
        boolean flag = info == null || info.isEmpty() || info.get(identity_in_map_key) == null || info.get(password_in_map_key) == null;
        if (!flag) {
            Object status=info.get(user_status_in_map_key);
            if(status!=null) {
                String userStatus=status.toString();
                if(user_status_forbidden.equals(userStatus)){//禁用账号
                    throw new AccountForbiddenException("AccountForbiddenException");
                }
               if(user_status_locked.equals(userStatus)){//账号锁定
                   throw new AccountLockedException("AccountLockedException");
               }
            }
            return new SimpleAuthenticationInfo(info.get(identity_in_map_key), info.get(password_in_map_key), getName());
        } else {
            throw new UnknownAccountException("UnknownAccountException");//没找到帐号;
        }
    }

    /**
     * 获取授权信息
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (!principals.isEmpty() && principals.fromRealm(getName()).size() > 0) {
            Object id = principals.fromRealm(getName()).iterator().next();
            if (id != null) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                if (enableRoles && enablePerms) {
                    Map<String, Collection<String>> rolesAndPerms = realmDao.getUserRolesAndPerms(id);
                    Collection<String> roles = rolesAndPerms.get(roles_in_map_key);
                    Collection<String> perms = rolesAndPerms.get(perms_in_map_key);
                    if (roles != null && !roles.isEmpty()) {
                        info.addRoles(roles);
                    }
                    if (perms != null && !perms.isEmpty()) {
                        info.addStringPermissions(perms);
                    }
                } else if (enableRoles && !enablePerms) {
                    Collection<String> perms = realmDao.getPermissions(id);
                    if (perms != null && !perms.isEmpty()) {
                        info.addStringPermissions(perms);
                    }
                } else if (enablePerms && !enableRoles) {
                    Collection<String> roles = realmDao.getRoles(id);
                    if (roles != null && !roles.isEmpty()) {
                        info.addRoles(roles);
                    }
                }
                return info;
            } else {
                return null;
            }
        } else
            return null;
    }


    /**
     * 获取认证信息在缓存中的键值
     * 此方法可以自定义认证信息在缓存中的键值，可以使用唯一标识和信息对应的方式
     * 此处的principals为此类中doGetAuthenticationInfo方法设置的principal，
     * 如果此realm前面还有realm，将有多个principal
     *
     * @param principals 凭证
     * @return key
     */
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        if (!principals.isEmpty() && principals.fromRealm(getName()).size() > 0) {
            Object id = principals.fromRealm(getName()).iterator().next();
            if (id != null) {
                return "DRZ_" + id;
            }
        }
        return null;
    }

    /**
     * 此方法登录时不会调用，且使用缓存时才调用此方法，默认使用传入的token键值作为缓存key，
     * 此方法在登出时调用，返回值必须和
     * {@link #getAuthenticationCacheKey(org.apache.shiro.authc.AuthenticationToken)}相同
     * <p/>
     * {@link org.apache.shiro.realm.AuthenticatingRealm#getAuthenticationCacheKey(org.apache.shiro.subject.PrincipalCollection)}
     *
     * @param principals
     * @return
     */
    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        if (!principals.isEmpty() && principals.fromRealm(getName()).size() > 0) {
            Object id = principals.fromRealm(getName()).iterator().next();
            if (id != null) {
                return "DRC_" + id;
            }
        }
        return null;
    }

    /**
     * 认证缓存key，登录时调用，默认使用token值，使用缓存时才调用此方法
     * {@link org.apache.shiro.realm.AuthenticatingRealm#getAuthenticationCacheKey(org.apache.shiro.authc.AuthenticationToken)}
     *
     * @param token token
     * @return key
     */
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        UsernamePasswordToken simpleToken = (UsernamePasswordToken) token;
        Object id = realmDao.getUniqueIdentity(simpleToken.getUsername().toLowerCase());
        if (id != null) {
            return "DRC_" + id;
        }
        return null;
    }

    public static boolean isEnablePerms() {
        return enablePerms;
    }

    public static void setEnablePerms(boolean enablePerms) {
        DrRealm.enablePerms = enablePerms;
    }

    public static boolean isEnableRoles() {
        return enableRoles;
    }

    public static void setEnableRoles(boolean enableRoles) {
        DrRealm.enableRoles = enableRoles;
    }

    public static String getIdentity_in_map_key() {
        return identity_in_map_key;
    }

    public static void setIdentity_in_map_key(String identity_in_map_key) {
        DrRealm.identity_in_map_key = identity_in_map_key;
    }

    public static String getPassword_in_map_key() {
        return password_in_map_key;
    }

    public static void setPassword_in_map_key(String password_in_map_key) {
        DrRealm.password_in_map_key = password_in_map_key;
    }

    public static String getRoles_in_map_key() {
        return roles_in_map_key;
    }

    public static void setRoles_in_map_key(String roles_in_map_key) {
        DrRealm.roles_in_map_key = roles_in_map_key;
    }

    public static String getPerms_in_map_key() {
        return perms_in_map_key;
    }

    public static void setPerms_in_map_key(String perms_in_map_key) {
        DrRealm.perms_in_map_key = perms_in_map_key;
    }

    public static String getUser_status_in_map_key() {
        return user_status_in_map_key;
    }

    public static void setUser_status_in_map_key(String user_status_in_map_key) {
        DrRealm.user_status_in_map_key = user_status_in_map_key;
    }

    public static String getUser_status_forbidden() {
        return user_status_forbidden;
    }

    public static void setUser_status_forbidden(String user_status_forbidden) {
        DrRealm.user_status_forbidden = user_status_forbidden;
    }

    public static String getUser_status_locked() {
        return user_status_locked;
    }

    public static void setUser_status_locked(String user_status_locked) {
        DrRealm.user_status_locked = user_status_locked;
    }

    public static String getAuthenticationFiltername() {
        return authenticationFiltername;
    }

    public static void setAuthenticationFiltername(String authenticationFiltername) {
        DrRealm.authenticationFiltername = authenticationFiltername;
    }

    public RealmDao getRealmDao() {
        return realmDao;
    }

    public void setRealmDao(RealmDao realmDao) {
        this.realmDao = realmDao;
    }
}
