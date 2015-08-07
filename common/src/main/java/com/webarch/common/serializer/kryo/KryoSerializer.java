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
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * serialization.kryo
 * functional describe:Kryo序列化器封装，用于池式实现。
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/4/9 11:33
 */
public class KryoSerializer {
    private static final Logger logger= LoggerFactory.getLogger(KryoSerializer.class);
    private static final int DEFAULT_BUFFER_SIZE=1024;
    //kryo
    private static final Kryo kryo = new Kryo();
    private static int BUFFER_SIZE = DEFAULT_BUFFER_SIZE;
    private Output output = new Output(BUFFER_SIZE, -1);
    private Input input = new Input();
    //简单属性设置
    static {
        kryo.setReferences(false);
    }

    public KryoSerializer() {
    }
    /**
     * 构造函数，注入时需提供注册类及对应的序列化器
     *
     * @param registerObjects 注册类及对应的序列化器
     */
    public KryoSerializer(Map<Class, Serializer> registerObjects) {
        //初始化注册类
        if (registerObjects != null && !registerObjects.isEmpty()) {
            logger.debug("开始注册序列化类及其序列化器！");
            Iterator<Class> iterator = registerObjects.keySet().iterator();
            int i = 1;
            while (iterator.hasNext()) {
                Class clazz = iterator.next();
                logger.info("注册类："+clazz.getName());
                Serializer serializer = registerObjects.get(clazz);
                if (serializer == null) {
                    kryo.register(clazz, i);
                } else {
                    kryo.register(clazz, serializer, i);
                }
                i++;
            }
            logger.debug("注册序列化类及其序列化器结束！");
        }
    }

    /**
     * 序列化
     * @param obj
     * @return
     * @throws IOException
     */
    public byte[] serialize(Object obj) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            output.clear();//清空
            kryo.writeClassAndObject(output, obj);
            return output.toBytes();
        }finally{
            obj=null;
        }
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws IOException
     */
    public Object deserialize(byte[] bytes) throws IOException {
        if(bytes == null || bytes.length == 0)
            return null;
        try {
            input.setBuffer(bytes, 0, bytes.length);
            return kryo.readClassAndObject(input);
        } finally {
           bytes=null;
        }
    }

    /**
     * 序列化器名称
     *
     * @return
     */
    public String name() {
        return "Kryo-Serializer";
    }

    public static Kryo getKryo() {
        return kryo;
    }

    public static int getBufferSize() {
        return BUFFER_SIZE;
    }

    public static void setBufferSize(int bufferSize) {
        BUFFER_SIZE = bufferSize;
    }
}
