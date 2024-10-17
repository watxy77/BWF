package com.bwf.core.support.singletonBean;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    @Nullable
    Object getSingleton(String beanName);
    boolean containsSingleton(String beanName);
    String[] getSingletonNames();
    int getSingletonCount();
}
