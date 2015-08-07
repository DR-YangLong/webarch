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
package com.webarch.common.serializer.kryo;

import java.io.IOException;

/**
 * serialization.kryo
 * functional describe:kryo序列化池接口
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/4/9 13:19
 */
public interface KryoPool {
    KryoSerializer get();

    /**
     * 序列化器添加到池中
     * @param serializer
     */
    void offer(KryoSerializer serializer);

    /***
     * 序列化
     * @param obj
     * @return
     * @throws IOException
     */
    byte[] serialize(Object obj) throws IOException;

    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws IOException
     */
    Object deserialize(byte[] bytes) throws IOException;
}
