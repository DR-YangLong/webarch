package com.webarch.model;

import java.util.Date;

public class SysPermission {
    
    private Integer permId;

    private String permName;

    private String permMark;

    private String permCreater;
    
    private Date permTime;
    
    private String permStatus;
    
    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public String getPermName() {
        return permName;
    }
    
    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermMark() {
        return permMark;
    }
    
    public void setPermMark(String permMark) {
        this.permMark = permMark;
    }

    public String getPermCreater() {
        return permCreater;
    }

    public void setPermCreater(String permCreater) {
        this.permCreater = permCreater;
    }
    
    public Date getPermTime() {
        return permTime;
    }

    public void setPermTime(Date permTime) {
        this.permTime = permTime;
    }

    public String getPermStatus() {
        return permStatus;
    }

    public void setPermStatus(String permStatus) {
        this.permStatus = permStatus;
    }
}