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
import com.webarch.common.shiro.distribution.DistributeCacheRpository;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能或描述：redis cache dao
 *
 * @Author: DR.YangLong
 * @Date: 14-9-30
 * @Time: 上午10:13
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
@SuppressWarnings({"unchecked"})
public class RedisRepository<K,V> implements DistributeCacheRpository<K,V> {
    private static final Logger logger= LoggerFactory.getLogger(RedisRepository.class);
    private static final String REDIS_PREFIX_REPO="dr_redis_repo:";
    /**
     * redis spring template{@link org.springframework.data.redis.core.RedisTemplate}
     */
    private RedisTemplate<String,V> redisTemplate;

    /**
     * cache中key前缀
     */
    private String keyPrefix=REDIS_PREFIX_REPO;

    /**
     * key的序列化器
     */
    private RedisSerializer redisSerializer;

    public RedisRepository() {
    }

    public RedisRepository(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisRepository(String keyPrefix,RedisTemplate<String, V> redisTemplate) {
        this.keyPrefix = keyPrefix;
        this.redisTemplate = redisTemplate;
    }

    public RedisRepository(RedisTemplate<String, V> redisTemplate, String keyPrefix, RedisSerializer redisSerializer) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = keyPrefix;
        this.redisSerializer = redisSerializer;
    }

    public V get(K key) {
        return redisTemplate.opsForValue().get(generateKey(key));
    }

    public V add(K key, V value) {
        String keys=generateKey(key);
        V previous=redisTemplate.opsForValue().get(keys);
        redisTemplate.opsForValue().set(keys,value);
        return previous;
    }

    public V delete(K key) {
        String keys=generateKey(key);
        V previous=redisTemplate.opsForValue().get(keys);
        redisTemplate.delete(keys);
        return previous;
    }

    public void clear() {
        Set<String> keySet = redisTemplate.keys(this.getKeyPrefix()+"*");
        redisTemplate.delete(keySet);
    }

    public int getSize() {
        String newKey = this.getKeyPrefix() + "*";
        Set<String> keySet = redisTemplate.keys(newKey);
        if (keySet == null) {
            return 0;
        }
        return keySet.size();
    }

    public Set<K> getKeys() {
        String newKey = generateKey("*");
        Set<String> keySet = redisTemplate.keys(newKey);
        Pattern pattern = Pattern.compile(this.getKeyPrefix() + "(.*)");
        Set<K> kSet = new HashSet<K>(keySet.size());
        for (String key : keySet) {
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                K k = (K) redisSerializer.deserialize(matcher.group(1).getBytes());
                kSet.add(k);
            }
        }
        return kSet;
    }

    public Collection<V> getValues() {
        String newKey = generateKey("*");
        Set<String> keySet = redisTemplate.keys(newKey);
        List<V> valueList = null;
        if (keySet != null && keySet.size() > 0) {
            valueList = redisTemplate.opsForValue().multiGet(keySet);
        }
        return valueList;
    }

    private String generateKey(Object key) {
        byte[] keyBytes=redisSerializer.serialize(key);
        byte[] newKey= ArrayUtils.addAll(redisSerializer.serialize(this.getKeyPrefix()), keyBytes);
        logger.debug("生成的redis键值："+ ArrayUtils.toString(newKey));
        return ArrayUtils.toString(newKey);
    }

    public RedisTemplate<String, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public RedisSerializer getRedisSerializer() {
        return redisSerializer;
    }

    public void setRedisSerializer(RedisSerializer redisSerializer) {
        this.redisSerializer = redisSerializer;
    }
}
