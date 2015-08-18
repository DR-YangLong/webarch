/*
        Copyright  DR.YangLong

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package com.webarch.common.shiro.authentication;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.Map;

/**
 * functional describe:查询用户授权及认证信息
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/5/14 13:38
 *
 */
public class ShiroRealm extends AuthorizingRealm {
    /**
     * default value
     */
    private static final String DEFAULT_PWD_KEY = "pwd";
    private static final String DEFAULT_IDENTITY_KEY = "id";
    private static final String DEFAULT_ROLES_KEY = "roles";
    private static final String DEFAULT_PERMS_KEY = "perms";
    /**
     * 是否使用权限
     */
    public static boolean enablePerms = false;
    /**
     * 是否使用角色
     */
    public static boolean enableRoles = true;
    /**
     * 用户身份唯一标识在Map中的key
     */
    private String identity_in_map_key = DEFAULT_IDENTITY_KEY;
    /**
     * 密码在map中的key
     */
    private String password_in_map_key = DEFAULT_PWD_KEY;
    /**
     * 角色在map中的KEY
     */
    private String roles_in_map_key = DEFAULT_ROLES_KEY;
    /**
     * 权限在map中的KEY
     */
    private String perms_in_map_key = DEFAULT_PERMS_KEY;

    /**
     * 获取认证和授权信息的服务接口
     */
    private RealmService realmService;

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<String, Object> info = realmService.getUserUniqueIdentityAndPassword(token.getUsername());
        boolean flag = info == null || info.isEmpty() || info.get(identity_in_map_key) == null || info.get(password_in_map_key) == null;
        if (!flag) {
            return new SimpleAuthenticationInfo(info.get(identity_in_map_key), info.get(password_in_map_key), getName());
        } else {
            throw new UnknownAccountException("UnknownAccountException");//没找到帐号;
        }
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (!principals.isEmpty() && principals.fromRealm(getName()).size() > 0) {
            Object id = principals.fromRealm(getName()).iterator().next();
            if (id != null) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                if (enableRoles && enablePerms) {
                    Map<String, Collection<String>> rolesAndPerms = realmService.getUserRolesAndPerms(id);
                    Collection<String> roles = rolesAndPerms.get(roles_in_map_key);
                    Collection<String> perms = rolesAndPerms.get(perms_in_map_key);
                    if (roles != null && !roles.isEmpty()) {
                        info.addRoles(roles);
                    }
                    if (perms != null && !perms.isEmpty()) {
                        info.addStringPermissions(perms);
                    }
                } else if (enablePerms && !enableRoles) {
                    Collection<String> perms = realmService.getPermissions(id);
                    if (perms != null && !perms.isEmpty()) {
                        info.addStringPermissions(perms);
                    }
                } else if (enableRoles && !enablePerms) {
                    Collection<String> roles = realmService.getRoles(id);
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

    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        if (!principals.isEmpty() && principals.fromRealm(getName()).size() > 0) {
            Object id = principals.fromRealm(getName()).iterator().next();
            if (id != null) {
                return "Autz_" + id;
            }
        }
        return null;
    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        UsernamePasswordToken simpleToken = (UsernamePasswordToken) token;
        Object id = realmService.getUniqueIdentity(simpleToken.getUsername().toLowerCase());
        if (id != null) {
            return "Autc_" + id;
        }
        return null;
    }

    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        if (!principals.isEmpty() && principals.fromRealm(getName()).size() > 0) {
            Object id = principals.fromRealm(getName()).iterator().next();
            if (id != null) {
                return "Autc_" + id;
            }
        }
        return null;
    }

    public RealmService getRealmService() {
        return realmService;
    }

    public void setRealmService(RealmService realmService) {
        this.realmService = realmService;
    }

    public String getPerms_in_map_key() {
        return perms_in_map_key;
    }

    public void setPerms_in_map_key(String perms_in_map_key) {
        this.perms_in_map_key = perms_in_map_key;
    }

    public String getRoles_in_map_key() {
        return roles_in_map_key;
    }

    public void setRoles_in_map_key(String roles_in_map_key) {
        this.roles_in_map_key = roles_in_map_key;
    }

    public String getPassword_in_map_key() {
        return password_in_map_key;
    }

    public void setPassword_in_map_key(String password_in_map_key) {
        this.password_in_map_key = password_in_map_key;
    }

    public String getIdentity_in_map_key() {
        return identity_in_map_key;
    }

    public void setIdentity_in_map_key(String identity_in_map_key) {
        this.identity_in_map_key = identity_in_map_key;
    }

    public static boolean isEnablePerms() {
        return enablePerms;
    }

    public static void setEnablePerms(boolean enablePerms) {
        ShiroRealm.enablePerms = enablePerms;
    }

    public static boolean isEnableRoles() {
        return enableRoles;
    }

    public static void setEnableRoles(boolean enableRoles) {
        ShiroRealm.enableRoles = enableRoles;
    }
}
