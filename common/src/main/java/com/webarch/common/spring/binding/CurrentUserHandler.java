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

package com.webarch.common.spring.binding;

import com.webarch.common.spring.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p/>
 * functional describe:get user object from session and binding to parameter.<br/>
 * eg:
 *<mvc:annotation-driven>
 *  <mvc:argument-resolvers>
 *      <bean class="CurrentUserHandler" />
 *  </mvc:argument-resolvers>
 *</mvc:annotation-driven>
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/7/21
 */
public class CurrentUserHandler implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if(methodParameter.hasParameterAnnotation(CurrentUser.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        CurrentUser currentUser=methodParameter.getParameterAnnotation(CurrentUser.class);
        String key=currentUser.KeyInSession();
        return ((HttpServletRequest)nativeWebRequest.getNativeRequest()).getSession().getAttribute(key);
    }
}
