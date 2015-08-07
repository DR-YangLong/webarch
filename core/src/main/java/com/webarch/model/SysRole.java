package com.webarch.model;

import java.util.Date;

public class SysRole {
    
    private Integer roleId;

    private String roleName;

    private String roleMark;

    private String roleCreater;

    private Date roleCreatetime;
    
    private String roleStatus;

    public Integer getRoleId() {
        return roleId;
    }

    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    
    public String getRoleName() {
        return roleName;
    }

    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
    public String getRoleMark() {
        return roleMark;
    }

    
    public void setRoleMark(String roleMark) {
        this.roleMark = roleMark;
    }

    
    public String getRoleCreater() {
        return roleCreater;
    }

    
    public void setRoleCreater(String roleCreater) {
        this.roleCreater = roleCreater;
    }

    
    public Date getRoleCreatetime() {
        return roleCreatetime;
    }

    
    public void setRoleCreatetime(Date roleCreatetime) {
        this.roleCreatetime = roleCreatetime;
    }

    
    public String getRoleStatus() {
        return roleStatus;
    }

    
    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }
}