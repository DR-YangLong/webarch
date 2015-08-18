/*
 * Copyright  DR.YangLong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webarch.common.constant;

/**
 * package:common.constant<br/>
 * functional describe:API接口常量
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/5/27 19:09
 */
public class APIconstant {
    private static String APP_SECRET_KEY = "appSecret";
    private static String TABLE_NAME_KEY = "tableName";
    private static String COM_ID_KEY = "comId";
    private static String COM_APP_NAME_KEY = "appName";
    private static String COM_APP_DOMAIN_KEY = "appDomain";

    public APIconstant() {
    }

    public static String getAppSecretKey() {
        return APP_SECRET_KEY;
    }

    public static void setAppSecretKey(String appSecretKey) {
        APP_SECRET_KEY = appSecretKey;
    }

    public static String getTableNameKey() {
        return TABLE_NAME_KEY;
    }

    public static void setTableNameKey(String tableNameKey) {
        TABLE_NAME_KEY = tableNameKey;
    }

    public static String getComIdKey() {
        return COM_ID_KEY;
    }

    public static void setComIdKey(String comIdKey) {
        COM_ID_KEY = comIdKey;
    }

    public static String getComAppNameKey() {
        return COM_APP_NAME_KEY;
    }

    public static void setComAppNameKey(String comAppNameKey) {
        COM_APP_NAME_KEY = comAppNameKey;
    }

    public static String getComAppDomainKey() {
        return COM_APP_DOMAIN_KEY;
    }

    public static void setComAppDomainKey(String comAppDomainKey) {
        COM_APP_DOMAIN_KEY = comAppDomainKey;
    }
}
