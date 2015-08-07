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
package com.webarch.common.redis.repository;

import java.util.concurrent.TimeUnit;

/**
 * 功能或描述：简单DAO，实现字符键值对的CRUD
 *
 * @Author: DR.YangLong
 * @Date: 14-9-24
 * @Time: 下午1:49
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
public interface RedisSimpleDao {
    /**
     * 添加
     *
     * @param key   键值
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 添加并设置存活时间
     *
     * @param key   键值
     * @param value 值
     * @param time  时间
     * @param unit 时间单位
     */
    void set(String key, String value, long time, TimeUnit unit);

    /**
     * 删除
     *
     * @param key 键值
     */
    void delete(String key);

    /**
     * 获取
     *
     * @param key 键值
     * @return String, 值
     */
    String get(String key);

}
