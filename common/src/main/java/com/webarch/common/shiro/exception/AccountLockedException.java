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
 * 功能或描述：帐号锁定异常
 *
 * @Author: DR.YangLong
 * @Date: 14-9-26
 * @Time: 下午5:22
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
public class AccountLockedException extends AuthenticationException {

    public AccountLockedException() {
        super();
    }

    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException(Throwable cause) {
        super(cause);
    }

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
