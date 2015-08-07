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

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 功能或描述：redis抽象DAO
 *
 * @Author: DR.YangLong
 * @Date: 14-9-24
 * @Time: 上午11:04
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
public abstract class AbstractRedisRepository<K, V> {
    protected RedisTemplate<K, V> redisTemplate;

    /**
     * 获取串行化工具
     *
     * @return String
     */
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    public RedisTemplate<K, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
