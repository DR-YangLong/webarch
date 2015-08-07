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
 *functional describe:账号禁止
 *
 * @author DR.YangLong
 *         email:410357434@163.com
 * @version 1.0  datetime:2014/12/12 16:29
 */
public class AccountForbiddenException extends AuthenticationException {
    public AccountForbiddenException() {
    }

    public AccountForbiddenException(String message) {
        super(message);
    }

    public AccountForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccountForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
