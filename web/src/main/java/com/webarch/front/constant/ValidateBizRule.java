package com.webarch.front.constant;

/**
 * package: com.webarch.front.constant <br/>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/10 20:24
 */
public enum ValidateBizRule {
    LOGIN_VALIDATE("LOGIN","登录");

    private String key;
    private String remark;

    public static String getValidateKey(String ruleName){
        switch (ruleName){
            case "1":return LOGIN_VALIDATE.getKey();
            default:return null;
        }
    }

    ValidateBizRule(String key, String remark) {
        this.key = key;
        this.remark = remark;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
