package com.bwf.core.beans.factory;

import com.bwf.core.beans.RootBeanDefinition;
import com.bwf.core.exception.BeansException;

public interface AutowireCapableBeanFactory {
    Object createBean(String className, RootBeanDefinition mbd) throws BeansException;
    void preInstantiateSingletons(String className, Class<?> clazz) throws BeansException;
    void destroyBean(Object existingBean);
}
