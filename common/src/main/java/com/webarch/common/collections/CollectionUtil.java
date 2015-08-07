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
package com.webarch.common.collections;

import java.util.Collection;
import java.util.List;

/**
 * functional describe:集合类工具
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/5/14 16:02
 */
public class CollectionUtil {

    /**
     * 使用关键字NEW创建MAP时，容量必须为2的倍数，
     * 可将容量值传入此方法获取正确容量
     *
     * @param number 容量
     * @return 修正后的容量
     */
    public static int getMapRightCapacity(int number) {
        return (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return true/false
     */
    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 将字符链表中的值取出，转换为String
     *
     * @param suffix 后缀，每个值中间间隔的符号
     * @param list   数组
     * @return String/null
     */
    public static String stringList2String(final String suffix, final List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i);
            if (value != null && !"".equals(value)) {
                if (i < (list.size() - 1)) {
                    builder.append(value).append(suffix);
                } else {
                    builder.append(value);
                }
            }
        }
        return builder.toString();
    }
}
