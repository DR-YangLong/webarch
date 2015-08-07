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
package com.webarch.common.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 功能或描述：验证码不匹配异常
 *
 * @author DR.YangLong
 *         date 14-10-13
 *         time 上午9:29
 *         email 410357434@163.com
 * @version 1.0
 * @since 1.0
 * module 修正:            日期：
 */
public class VerifyErrorException extends AuthenticationException {
    public VerifyErrorException() {
        super();
    }

    public VerifyErrorException(String message) {
        super(message);
    }

    public VerifyErrorException(Throwable cause) {
        super(cause);
    }

    public VerifyErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
