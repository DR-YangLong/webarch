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
package com.webarch.common.shiro.distribution.spring;

import com.webarch.common.shiro.distribution.DistributeCache;
import com.webarch.common.shiro.distribution.DistributeCacheFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 功能或描述：分布式缓存redis实现
 *
 * @Package com.shiro.distribution.redis
 * @Author: DR.YangLong
 * @Date: 14-9-30
 * @Time: 上午11:27
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
public class RedisCacheFactory implements DistributeCacheFactory {
    private static final String DEFAULT_CACHE_NAME="dr_redis_repo:";

    private String cacheName=DEFAULT_CACHE_NAME;
    /**
     * session工厂
     */
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * spring-data-redis template序列化Key使用的序列化器
     */
    private RedisSerializer keySerializer;

    /**
     * spring-data-redis template序列化Value使用的序列化器
     */
    private RedisSerializer valueSerializer;

    /**
     * 由于使用spring-data-redis,所以对结果集的查询不能使用模糊匹配等查询，造成了再次封装，
     * 这个序列化器用来序列化对象成为String类型的key，再传递给上面的 {@link #keySerializer}
     * 可以使用Jedis直接操作redis以获取较高的性能
     */
    private RedisSerializer daoKeySerializer;

    public void destroy() {
        jedisConnectionFactory.destroy();
    }

    public <K, V> DistributeCache<K, V> getCache(final String name) {
        if(name!=null){
            cacheName=name;
        }
        RedisTemplate<String, V> redisTemplate = new RedisTemplate<String,V>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
        RedisRepository<K, V> redisRepository = new RedisRepository<K,V>(redisTemplate,cacheName,daoKeySerializer);
        return new DistributeCache<K,V>(redisRepository,name);
    }

    public JedisConnectionFactory getJedisConnectionFactory() {
        return jedisConnectionFactory;
    }

    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public RedisSerializer getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    public RedisSerializer getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public RedisSerializer getDaoKeySerializer() {
        return daoKeySerializer;
    }

    public void setDaoKeySerializer(RedisSerializer daoKeySerializer) {
        this.daoKeySerializer = daoKeySerializer;
    }
}
