package com.webarch.front.constant;

import java.io.Serializable;

/**
 * package: com.webarch.front.constant <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/17 19:21
 */
public class ResultVo implements Serializable{
    private static final boolean DEFAULT_SUCCESS=false;
    private static final long serialVersionUID = 2137866166139348388L;

    private boolean success=DEFAULT_SUCCESS;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
