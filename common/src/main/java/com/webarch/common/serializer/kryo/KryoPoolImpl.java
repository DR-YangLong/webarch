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

import com.esotericsoftware.kryo.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * serialization.kryo
 * functional describe:kryo池,此对象需要单例模式，<br/>
 * 初始化后每次使用调用<code>serialize</code>进行序列化，<code>deserialize</code>反序列化<br/>
 * 自定义序列化器：使用<code>Map<Class, Serializer> registerObjects</code>进行类的序列化器定制<br/>
 * 此实现方式以空间换时间
 * {@link KryoSerializer}
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/4/9 13:36
 */
public class KryoPoolImpl implements KryoPool{
    private static final Logger logger= LoggerFactory.getLogger(KryoPoolImpl.class);
    //默认池大小
    private static final int DEFAULT_INIT_SIZE=1500;
    //注册类及对应的序列化器
    private Map<Class, Serializer> registerObjects;
    //初始池大小
    private Integer init_size=DEFAULT_INIT_SIZE;
    //池
    private final Deque<KryoSerializer> pool=new ConcurrentLinkedDeque<KryoSerializer>();

    public KryoPoolImpl() {
        this.init(DEFAULT_INIT_SIZE);
    }

    public KryoPoolImpl(Map<Class, Serializer> registerObjects) {
        this.registerObjects = registerObjects;
        this.init(DEFAULT_INIT_SIZE);
    }

    public KryoPoolImpl(Map<Class, Serializer> registerObjects,Integer init_size) {
        this.registerObjects = registerObjects;
        this.init_size=(init_size==null||init_size<=0)?DEFAULT_INIT_SIZE:init_size;
        this.init(this.init_size);
    }

    /**
     * 初始化池
     * @param init_size
     */
    private void init(Integer init_size){
        logger.debug("开始初始化Kryo序列化池：\n初始大小【"+init_size+"】");
        while (init_size-->0){
            this.offer(this.createInstance());
        }
        logger.debug("Kryo序列化池序列化结束！");
    }

    public KryoSerializer get() {
        KryoSerializer serializer=pool.pollFirst();
        return serializer==null?createInstance():serializer;
    }

    public void offer(KryoSerializer serializer) {
            pool.addLast(serializer);
    }

    private KryoSerializer createInstance(){
        KryoSerializer serializer=new KryoSerializer(registerObjects);
        return serializer;
    }

    public byte[] serialize(Object obj) throws IOException {
        if(obj==null){
            throw new RuntimeException("序列化对象不能为NULL！");
        }
        KryoSerializer serializer=null;
        try {
            serializer=this.get();
            return serializer.serialize(obj);
        } finally {
            this.offer(serializer);
            obj=null;
        }
    }

    public Object deserialize(byte[] bytes) throws IOException {
        if(bytes==null||bytes.length<=0){
            throw new RuntimeException("反序列化数组不能为NULL或长度不能小于等于0！");
        }
        KryoSerializer serializer=null;
        try {
            serializer=this.get();
            return serializer.deserialize(bytes);
        } finally {
            this.offer(serializer);
            bytes=null;
        }
    }

    public Integer getInitSize() {
        return init_size;
    }
}
