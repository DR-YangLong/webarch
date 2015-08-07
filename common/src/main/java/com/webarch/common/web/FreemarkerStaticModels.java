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

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * freemarker 静态类Map类
 * 将工具类放入Map中，在模版中使用
 * @author leon
 * @create date 2012-12-24
 */
public class FreemarkerStaticModels extends HashMap<Object, Object> {

	private static final long serialVersionUID = -1283328715025910827L;

	private static FreemarkerStaticModels FREEMARKER_STATIC_MODELS;

	private Properties staticModels;

	private FreemarkerStaticModels() {

	}

	public static FreemarkerStaticModels getInstance() {
		if (FREEMARKER_STATIC_MODELS == null) {
			FREEMARKER_STATIC_MODELS = new FreemarkerStaticModels();
		}
		return FREEMARKER_STATIC_MODELS;
	}

	public Properties getStaticModels() {
		return staticModels;
	}

	public void setStaticModels(Properties staticModels) {
		if (this.staticModels == null && staticModels != null) {
			this.staticModels = staticModels;
			Set<String> keys = this.staticModels.stringPropertyNames();
			for (String key : keys) {
				FREEMARKER_STATIC_MODELS.put(key, useStaticPackage(this.staticModels.getProperty(key)));
			}
		}
	}

	public static TemplateHashModel useStaticPackage(String packageName) {
		try {
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();
			TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName);
			return fileStatics;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
