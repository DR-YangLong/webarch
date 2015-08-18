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

/**
 * 功能或描述：分布式cache工场
 *
 * @Author: DR.YangLong
 * @Date: 14-9-30
 * @Time: 上午10:05
 * @Email: 410357434@163.com
 * @Version: 1.0
 * @Module: 修正:            日期：
 */
public interface DistributeCacheFactory{
    /**
     * 获取缓存
     * @param name 缓存名称
     * @param <K> 泛型键
     * @param <V> 泛型值
     * @return Cache
     */
    <K, V> DistributeCache<K, V> getCache(String name);

    /**
     * 清除
     */
    void destroy();
}
