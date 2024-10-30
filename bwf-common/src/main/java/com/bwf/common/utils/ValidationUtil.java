package com.bwf.common.utils;

import java.util.Collection;

public class ValidationUtil {
    public static boolean isEmpty(Collection<?> obj){
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(String obj){
        return (obj == null || "".equals(obj));
    }

    public static boolean isEmpty(Object[] obj){
        return (obj == null || obj.length == 0);
    }
}
