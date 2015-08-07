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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * com.shiro.distribution.redis
 * functional describe:Kryo实现的Redis序列化器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/1/21 11:33
 */
public class KryoSerialization{
    private static final Logger logger = LoggerFactory.getLogger(KryoSerialization.class);
    private static final byte[] EMPTY_BYTES = new byte[0];

    public byte[] serialize(Object object) throws SerializationException {
        logger.debug("Kryo序列化开始，时间：" + System.currentTimeMillis());
        Kryo kryo=new Kryo();
        kryo.register(SimpleSession.class, new JavaSerializer(),1);
        if (object == null) {
            return EMPTY_BYTES;
        }
        byte[] bytes = EMPTY_BYTES;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
            /*ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);*/
            Output output = new Output(byteStream);
            kryo.writeClassAndObject(output, object);
            output.close();
            bytes = byteStream.toByteArray();
            byteStream.close();
            logger.debug("Kryo序列化结束，时间：" + System.currentTimeMillis() + "\n大小：" + bytes.length);
        } catch (Exception e) {
            logger.error("kryo序列化错误！");
        }
        return bytes;
    }

    public Object deserialize(byte[] bytes) throws SerializationException {
        logger.debug("Kryo反序列化开始，时间：" + System.currentTimeMillis());
        Kryo kryo=new Kryo();
        kryo.register(SimpleSession.class, new JavaSerializer(),1);
        if (isEmpty(bytes)) {
            return null;
        }
        Object object = null;
        try {
            object = null;
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteStream);
            object = kryo.readClassAndObject(input);
            input.close();
            byteStream.close();
            logger.debug("Kryo反序列化结束，时间：" + System.currentTimeMillis());
        } catch (IOException e) {
            logger.error("Kryo反序列化失败！" + e.getMessage());
        }
        return object;
    }

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}
