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
package com.webarch.common.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 去掉浏览后缀附带的J2EE session id字符串，默认为JSESSIONID，使用初始化属性sessionFix来指定
 */
public class DisableUrlSessionFilter implements Filter {
	private static final String DEFAULT_SESSION_SIGN="JSESSIONID";
    private FilterConfig filterConfig;

	public void destroy() {
		this.filterConfig=null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (httpRequest.isRequestedSessionIdFromURL()) {  
            HttpSession session = httpRequest.getSession();
            if (session != null)  
                session.invalidate();  
        } 
		String url = httpRequest.getRequestURL().toString();
		String sessionFix=filterConfig.getInitParameter("sessionFix");
		sessionFix=(sessionFix==null||"".equals(sessionFix))?DEFAULT_SESSION_SIGN:sessionFix;
		sessionFix=";"+sessionFix+"=";
        int inx = url.indexOf(sessionFix);
        if(inx > 0)
        {
        	url = url.substring(0, inx);
        	httpResponse.sendRedirect(url);
        	return;
        }
        
		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
			public String encodeRedirectUrl(String url) {
				return url;
			}

			public String encodeRedirectURL(String url) {
				return url;
			}

			public String encodeUrl(String url) {
				return url;
			}

			public String encodeURL(String url) {
				return url;
			}
		};
		chain.doFilter(request, wrappedResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig=filterConfig;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
