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

package com.webarch.common.spring.annotation;

import java.lang.annotation.*;

/**
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p/>
 * functional describe:use this annotation to biding user object to controller's parameter<br/>
 * eg: public ModelAndView toIndex(@CurrentUser SysUser user)...will get SysUser from session and binding to user.<br/>
 * {@link spring.binding.CurrentUserHandler}
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/7/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface CurrentUser {
    String KeyInSession() default "user";
}
