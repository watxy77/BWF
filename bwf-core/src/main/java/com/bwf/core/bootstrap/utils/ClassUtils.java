package com.bwf.core.bootstrap.utils;
/**
 * @Author bjweijiannan
 * @description
 */
public abstract class ClassUtils {
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
}
