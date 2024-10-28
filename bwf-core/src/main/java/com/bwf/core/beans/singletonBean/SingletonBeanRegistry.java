package com.bwf.core.beans.singletonBean;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.reader.BeanDefinitionDocument;

public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    void registerSingleton(BeanDefinitionDocument bdd);
    @Nullable
    Object getSingleton(String beanName);
    boolean containsSingleton(String beanName);
    String[] getSingletonNames();
    int getSingletonCount();
}
