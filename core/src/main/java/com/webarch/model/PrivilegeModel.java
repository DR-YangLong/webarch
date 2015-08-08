package com.webarch.model;

import java.util.List;

/**
 * package: com.webarch.model <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/8 15:29
 */
public class PrivilegeModel {
    private String resUrl;
    private List<String> roleName;
    private List<String> permsName;

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

    public List<String> getPermsName() {
        return permsName;
    }

    public void setPermsName(List<String> permsName) {
        this.permsName = permsName;
    }
}
