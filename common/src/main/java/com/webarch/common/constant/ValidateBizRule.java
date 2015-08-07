/*
 * Copyright (c) 2015 传化科技服务有限公司(Transfar Group) All rights reserved
 */

package com.webarch.common.constant;

/**
 * package: com.transfar.greentech.constant </br>
 * functional describe:验证码规则定义
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/6/29 10:40
 */
public enum ValidateBizRule {
    LOGIN("1", "login_captcha", "登陆验证码");
    //业务规则代码
    private String ruleCode;
    //在缓存或session中的键
    private String ruleKey;
    //描述
    private String ruleMark;

    ValidateBizRule(String ruleCode, String ruleKey, String ruleMark) {
        this.ruleCode = ruleCode;
        this.ruleKey = ruleKey;
        this.ruleMark = ruleMark;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    public String getRuleMark() {
        return ruleMark;
    }

    public void setRuleMark(String ruleMark) {
        this.ruleMark = ruleMark;
    }

    public static String getValidateKey(String ruleCode) {
        if (ruleCode == null) return null;
        switch (ruleCode) {
            case "1":
                return LOGIN.getRuleKey();
            default:
                return null;
        }
    }
}
