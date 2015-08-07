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
package com.webarch.common.shiro.distribution.Jedis;

import com.webarch.common.shiro.distribution.DistributeCacheRpository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

/**
 * shiroexample.common.shiro.distribution.Jedis
 * functional describe:Jedis缓存类
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/4/13 15:40
 */
public class RedisCache implements DistributeCacheRpository {
    private static final transient Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private static final String DEFAULT_JEDIS_CACHENAME = "JEDIS_CACHE";
    private String name=DEFAULT_JEDIS_CACHENAME;//缓存名称，即存储时分组名称，将会作为KEY前缀作为redis的key

    public RedisCache(String name) {
        this.name = name;
    }


    public Object get(Object key) {
        return null;
    }

    public Object add(Object key, Object value) {
        return null;
    }

    public Object delete(Object key) {
        return null;
    }

    public void clear() {

    }

    public int getSize() {
        return 0;
    }

    public Set getKeys() {
        return null;
    }

    public Collection getValues() {
        return null;
    }
}
