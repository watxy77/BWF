package com.bwf.core.beans.utils;

import com.bwf.common.utils.StringUtils;

public class BeanUtil {
    public static String transformedBeanName(String className) {
        String beanName = "";
        if(StringUtils.isNotEmpty(className)){
            int startIndex = className.lastIndexOf(".");
            beanName = className.substring(startIndex + 1, className.length());
        }
        return beanName;
    }
}
