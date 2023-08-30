package com.atguigu.myssm.util;

/**
 * 字符串工具类
 */
public class StringUtil {

    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
