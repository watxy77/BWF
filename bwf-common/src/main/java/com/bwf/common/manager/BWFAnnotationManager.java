package com.bwf.common.manager;

import com.bwf.common.annotation.bootstrap.BWFBeanDefinition;

public interface BWFAnnotationManager {
    final static String SINGLETON = "singleton";
    final static String PROTOTYPE = "prototype";
    Object getInstance();
    Object createBean(BWFBeanDefinition BWFComponentBeanDefinition);
    Object getBean(String beanName);
    void createBWFBeanDefinition(String className, Class clazz);
    void scanSingleton();
}
