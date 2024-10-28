package com.bwf.core.bootstrap.utils;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

/**
 * @Author bjweijiannan
 * @description
 */
public abstract class ClassUtils {
    private static final String EMPTY_STRING = "";
    public static String getShortName(Class<?> clazz) {
        return getShortName(getQualifiedName(clazz));
    }

    public static String getQualifiedName(Class<?> clazz) {
        return clazz.getTypeName();
    }

    public static String getShortName(String className) {
        int lastDotIndex = className.lastIndexOf(46);
        int nameEndIndex = className.indexOf("$$");
        if (nameEndIndex == -1) {
            nameEndIndex = className.length();
        }

        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace('$', '.');
        return shortName;
    }

    public static String identityToString(@Nullable Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return obj.getClass().getName() + "@" + getIdentityHexString(obj);
    }

    public static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }
}
