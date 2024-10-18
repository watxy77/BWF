package com.bwf.core.beans.factory;

import com.bwf.core.exception.BeansException;

public interface AutowireCapableBeanFactory {
    Object createBean(String className, Class beanClass) throws BeansException;
    void destroyBean(Object existingBean);
}
