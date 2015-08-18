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

import com.webarch.common.io.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * functional describe:使用Jedis提供连接池，默认1个redis实例，redis部署采用双主热备，keepalive方案避免单点。
 * 使用载入classpath下的redis.properties文件来获取参数,
 * 配置文件的key值需要与Jedis配置的参数名保持一致
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/4/10 13:33
 */
public class RedisCacheProvider {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheProvider.class);
    //默认配置文件名
    private static final String PROP_FILE_NAME = "redis";
    //配置文件名
    private String propFileName = PROP_FILE_NAME;
    //连接池
    private static JedisPool pool;

    public RedisCacheProvider() {
        this.start();
    }

    public RedisCacheProvider(String propFileName) {
        this.propFileName = propFileName;
        this.start();
    }

    /**
     * 获取redis client
     *
     * @return
     */
    public static Jedis getCache() {
        return pool.getResource();
    }

    /**
     * 启动链接，初始化连接池
     */
    public void start() {
        Properties properties = PropertiesUtils.getProperties(this.propFileName);
        if (properties != null) {
            logger.debug("开始初始化Jedis连接池！");
            JedisPoolConfig config = new JedisPoolConfig();
            String host = PropertiesUtils.getProperty(properties, "host", "127.0.0.1");
            String password = PropertiesUtils.getProperty(properties, "password", null);
            int port = PropertiesUtils.getProperty(properties, "port", 6379);
            int timeout = PropertiesUtils.getProperty(properties, "timeout", 2000);
            int database = PropertiesUtils.getProperty(properties, "database", 0);
            //config.setWhenExhaustedAction((byte)PropertiesUtils.getProperty(properties, "whenExhaustedAction", 1));
            config.setMaxIdle(PropertiesUtils.getProperty(properties, "maxIdle", 10));
            config.setMinIdle(PropertiesUtils.getProperty(properties, "minIdle", 5));
            //config.setMaxActive(PropertiesUtils.getProperty(properties, "maxActive", 50));
            config.setMaxWaitMillis(PropertiesUtils.getProperty(properties, "maxWait", 100));
            config.setTestWhileIdle(PropertiesUtils.getProperty(properties, "testWhileIdle", false));
            config.setTestOnBorrow(PropertiesUtils.getProperty(properties, "testOnBorrow", true));
            config.setTestOnReturn(PropertiesUtils.getProperty(properties, "testOnReturn", false));
            config.setNumTestsPerEvictionRun(PropertiesUtils.getProperty(properties, "numTestsPerEvictionRun", 10));
            config.setMinEvictableIdleTimeMillis(PropertiesUtils.getProperty(properties, "minEvictableIdleTimeMillis", 1000));
            config.setSoftMinEvictableIdleTimeMillis(PropertiesUtils.getProperty(properties, "softMinEvictableIdleTimeMillis", 10));
            config.setTimeBetweenEvictionRunsMillis(PropertiesUtils.getProperty(properties, "timeBetweenEvictionRunsMillis", 10));
            config.setLifo(PropertiesUtils.getProperty(properties, "lifo", false));
            pool = new JedisPool(config, host, port, timeout, password, database);
            logger.debug("开始初始化Jedis连接池初始化结束！");
        }
    }

    /**
     * 关闭链接
     */
    public void stop() {
        pool.destroy();
    }

    /**
     * 释放资源
     * @param jedis  jedis instance
     * @param isBrokenResource resource is ok or not
     */
    public static void returnResource(Jedis jedis,boolean isBrokenResource) {
        if(null == jedis)
            return;
        if(isBrokenResource){
            pool.returnBrokenResource(jedis);
            jedis = null;
        }
        else
            pool.returnResource(jedis);
    }
}
