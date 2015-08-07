package com.webarch.model;

import java.util.Date;

public class SysResources {
    
    private Integer resId;
    
    private String resUrl;

    private String resCreater;

    private String resMark;

    private Date resTime;

    private String resStatus;
    
    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }
    
    public String getResUrl() {
        return resUrl;
    }
    
    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getResCreater() {
        return resCreater;
    }

    public void setResCreater(String resCreater) {
        this.resCreater = resCreater;
    }
    
    public String getResMark() {
        return resMark;
    }

    public void setResMark(String resMark) {
        this.resMark = resMark;
    }

    public Date getResTime() {
        return resTime;
    }

    public void setResTime(Date resTime) {
        this.resTime = resTime;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }
}