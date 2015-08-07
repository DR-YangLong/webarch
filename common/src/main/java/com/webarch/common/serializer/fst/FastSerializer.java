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
package com.webarch.common.serializer.fst;

import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.shiro.distribution.redis
 * functional describe:FST序列化器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/1/20 13:10
 */
public class FastSerializer {
    private static final Logger logger= LoggerFactory.getLogger(FastSerializer.class);
    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
    private static final byte[] EMPTY_BYTES=new byte[0];

    public byte[] serialize(Object o){
        logger.debug("FastSerialization序列化开始，时间："+System.currentTimeMillis());
        if(o==null){
            return EMPTY_BYTES;
        }
        byte[] bytes=conf.asByteArray(o);
        logger.debug("FastSerialization序列化结束，时间："+System.currentTimeMillis()+"\n大小："+bytes.length);
        return bytes;
    }

    public Object deserialize(byte[] bytes){
        logger.debug("FastSerialization反序列化开始，时间："+System.currentTimeMillis());
        if(bytes==null||bytes.length==0){
            return null;
        }
        Object o=conf.asObject(bytes);
        logger.debug("FastSerialization反序列化结束，时间："+System.currentTimeMillis());
        return o;
    }
}
