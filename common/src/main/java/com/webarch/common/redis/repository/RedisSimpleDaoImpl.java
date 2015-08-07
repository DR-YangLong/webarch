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
 * 功能或描述：redis简单DAO实现
 *
 * @Author: DR.YangLong
 * @Date: 14-9-24
 * @Time: 下午2:10
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
public class RedisSimpleDaoImpl extends AbstractRedisRepository<String,String> implements RedisSimpleDao {
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key, String value, long time,TimeUnit unit) {
        if(null==unit){//如果没有定单位，使用分钟
            unit=TimeUnit.MINUTES;
        }
        redisTemplate.opsForValue().set(key,value,time,unit);
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
