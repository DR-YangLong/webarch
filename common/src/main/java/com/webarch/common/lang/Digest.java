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
package com.webarch.common.lang;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * functional describe:字符串加解密工具类
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/5/13 20:50
 */
public class Digest {
    /**
     * 获取排序后的字符串组合源码
     *
     * @param values 参数
     * @return 参数排序后组合在一起的字符串
     */
    public static String generateSourceString(String... values) {
        List<String> list = Arrays.asList(values);
        Collections.sort(list);
        return StringUtils.join(list, "");
    }

    /**
     * sha1加密，单个参数将会直接加密，多个参数将排序组合拼接为一个字符串后加密，UTF-8
     *
     * @param sources 源字符串
     * @return 加密后的串
     */
    public static String signatureString(final String... sources) {
        String source;
        if (sources.length == 1) {
            source = sources[0];
        } else {
            source = generateSourceString(sources);
        }
        return DigestUtils.sha1Hex(source);
    }

    /**
     * 对字符串进行base64加密
     *
     * @param charset  字符集，默认UTF-8
     * @param soureces 源字符串
     * @return 字符数组，下标与<code>sources</code>参数位置对应
     */
    public static String[] base64Code(String charset, final String... soureces) {
        try {
            if (charset == null || "".equals(charset)) charset = "UTF-8";
            String[] results = new String[soureces.length];
            for (int i = 0; i < soureces.length; i++) {
                results[i] = Base64.encodeBase64URLSafeString(soureces[i].getBytes(charset));
            }
            return results;
        } catch (UnsupportedEncodingException E) {
            return null;
        }
    }

    /**
     * 解密base64编码
     *
     * @param charset 字符集，默认utf-8
     * @param sources 待解密字符串
     * @return 已解密字符串组成的数组，数组下标与<code>sources</code>下标对应
     */
    public static String[] base64Decode(String charset, final String... sources) {
        try {
            if (charset == null || "".equals(charset)) charset = "UTF-8";
            String[] results = new String[sources.length];
            for (int i = 0; i < sources.length; i++) {
                results[i] = new String(Base64.decodeBase64(sources[i]), charset);
            }
            return results;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void main(String args[]) {
        System.out.println(generateSourceString("sdfa", "ggggg", "wwwww"));
        System.out.println(signatureString("sdfa", "ggggg", "wwwww"));
        System.out.println("加密串"+ DigestUtils.md5Hex("杨龙"));
        System.out.println(StringUtils.join(base64Code("utf-8","46d045ff5190f6ea93739da6c0aa19bc", "ggggg", "wwwww"),"-"));
        System.out.println(StringUtils.join(base64Decode("utf-8","NDZkMDQ1ZmY1MTkwZjZlYTkzNzM5ZGE2YzBhYTE5YmM", "Z2dnZ2c", "d3d3d3c"),"-"));
    }
}
