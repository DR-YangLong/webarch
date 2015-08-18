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
package com.webarch.common.serializer.fsatjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * functional describe:FastJson序列化器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/1/20 11:30
 */
public class FastJsonSerializer {
    private static final Logger logger= LoggerFactory.getLogger(FastJsonSerializer.class);
    private static final byte[] EMPTY_BYTES=new byte[0];
    private static final String DEFAULT_CHARSET="UTF-8";

    public byte[] serialize(Object o) {
        logger.debug("fastjson序列化开始，时间："+System.currentTimeMillis());
        if(o==null){
            return EMPTY_BYTES;
        }
        SerializeWriter serializeWriter=new SerializeWriter();
        serializeWriter.config(SerializerFeature.SkipTransientField,false);
        serializeWriter.config(SerializerFeature.WriteClassName,true);
        serializeWriter.config(SerializerFeature.WriteDateUseDateFormat,true);
        JSONSerializer serializer = new JSONSerializer(serializeWriter);
        serializer.getPropertyFilters().add(new FastJsonShiroSessionFilter());
        serializer.write(o);
        byte[] bytes=serializeWriter.toBytes(DEFAULT_CHARSET);
        serializer.close();
        logger.debug("fastjson序列化结束，时间："+System.currentTimeMillis()+
                "\nfastjson字符串：\n"+new String(bytes)+"\n序列化后大小："+bytes.length);
        return bytes;
    }

    public Object deserialize(byte[] bytes) {
        logger.debug("fastjson反序列化开始，时间："+System.currentTimeMillis());
        if(bytes==null||bytes.length==0){
            return null;
        }
        Object o=JSON.parse(bytes);
        logger.debug("fastjson反序列化结束，时间："+System.currentTimeMillis());
        return o;
    }
}
