package com.bwf.common.manager;

import com.bwf.common.annotation.bootstrap.BWFBeanDefinition;

public interface BWFAnnotationManager {
    final static String SINGLETON = "singleton";
    Object getInstance();
    Object createBean(BWFBeanDefinition BWFBeanDefinition);
    Object getBean(String beanName);
    void createSingleton(String className, Class clazz);
    void scanSingleton();
}
