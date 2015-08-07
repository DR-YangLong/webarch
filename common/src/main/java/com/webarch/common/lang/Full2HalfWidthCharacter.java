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

/**
 * functional describe:全角字符转换半角字符，参照ASCII，CSDN文章
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/1/16 20:23
 */
public class Full2HalfWidthCharacter {
    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    private static final char HALF_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    private static final char HALF_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    private static final char FULL_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    private static final char FULL_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    private static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    private static final char FULL_SPACE = 12288; // 全角空格 12288

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    private static final char HALF_SPACE = ' '; // 半角空格


    /**
     * 全角转半角
     *
     * @param input String with full-width character
     * @return String half-width character
     */
    public static String fullChar2halfChar(final String input) {
        if (input == null) {
            return input;
        }
        StringBuffer buffer = new StringBuffer(input.length());
        char[] asiiChars = input.toCharArray();
        for (char asii : asiiChars) {
            if (asii >= FULL_CHAR_START && asii <= FULL_CHAR_END) {
                buffer.append((char) (asii - CONVERT_STEP));
            } else if (asii == FULL_SPACE) {
                buffer.append(HALF_SPACE);
            } else {
                buffer.append(asii);
            }
        }
        return buffer.toString();
    }

    /**
     * 半角转全角
     *
     * @param input String with half-width character
     * @return String full-width character
     */
    public static String halfChar2fullChar(final String input) {
        if (input == null) {
            return input;
        }
        StringBuffer buffer = new StringBuffer(input.length() * 2);
        char[] asiiChars = input.toCharArray();
        for (char asii : asiiChars) {
            if (asii >= HALF_CHAR_START && asii <= HALF_CHAR_END) {
                buffer.append((char) (asii + CONVERT_STEP));
            } else if (asii == HALF_SPACE) {
                buffer.append(FULL_SPACE);
            } else {
                buffer.append(asii);
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String fullChar = "ＡＢＣＤＥＦabcdfng 　";
        String halfChar = "ABCDEFａｄｕ　";
        char[] full = fullChar.toCharArray();
        char[] half = halfChar.toCharArray();
        String full2half = fullChar2halfChar(fullChar);
        String half2full = halfChar2fullChar(halfChar);
        System.out.println("全角转半角结果：" + full2half + "\n半角转全角" + half2full);
    }
}
