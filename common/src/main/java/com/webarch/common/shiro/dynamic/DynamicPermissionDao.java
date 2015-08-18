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
package com.webarch.common.shiro.dynamic;

import java.util.Map;

/**
 * functional describe:动态权限资源获取DAO接口,根据自己需要实现，
 * 从文件/数据库来加载权限map<ant url,comma-delimited chain definition>
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/1/17 11:46
 */
public interface DynamicPermissionDao {
  Map<String,String> findDefinitionsMap();
}
