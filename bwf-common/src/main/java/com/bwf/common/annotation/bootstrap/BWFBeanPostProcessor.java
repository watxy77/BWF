package com.bwf.common.annotation.bootstrap;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

public interface BWFBeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitiallization(Object bean, String beanName){
        return bean;
    }
    @Nullable
    default Object postProcessAfterInitiallization(Object bean, String beanName){
        return bean;
    }
}
