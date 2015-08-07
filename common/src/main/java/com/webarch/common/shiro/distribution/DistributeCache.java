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
package com.webarch.common.shiro.distribution;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * 功能或描述：分布式缓存接口
 *
 * @Package com.shiro.distribution
 * @Author: DR.YangLong
 * @Date: 14-9-30
 * @Time: 上午9:50
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
public class DistributeCache<K, V> implements Cache<K, V> {
    private static final String DEFAULT_CACHE_NAME="dr_redis_cache:";
    /**
     * cache dao
     */
    private DistributeCacheRpository<K, V> distributeCacheRpository;
    /**
     * the name of this cache
     */
    private String name=DEFAULT_CACHE_NAME;

    public DistributeCache() {
    }

    public DistributeCache(DistributeCacheRpository<K, V> distributeCacheRpository) {
        this.distributeCacheRpository = distributeCacheRpository;
    }

    public DistributeCache(DistributeCacheRpository<K, V> distributeCacheRpository, String name) {
        this.distributeCacheRpository = distributeCacheRpository;
        this.name = name;
    }

    public V get(K key) throws CacheException {
        return distributeCacheRpository.get(key);
    }

    public V put(K key, V value) throws CacheException {
        return distributeCacheRpository.add(key,value);
    }

    public V remove(K key) throws CacheException {
        return distributeCacheRpository.delete(key);
    }

    public void clear() throws CacheException {
        distributeCacheRpository.clear();
    }

    public int size() {
        return distributeCacheRpository.getSize();
    }

    public Set<K> keys() {
        return distributeCacheRpository.getKeys();
    }

    public Collection<V> values() {
        return distributeCacheRpository.getValues();
    }

    public DistributeCacheRpository<K, V> getDistributeCacheRpository() {
        return distributeCacheRpository;
    }

    public void setDistributeCacheRpository(DistributeCacheRpository<K, V> distributeCacheRpository) {
        this.distributeCacheRpository = distributeCacheRpository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
