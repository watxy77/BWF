package com.bwf.core.beans.factory;

public interface ListableBeanFactory extends BeanFactory{
    boolean containsBeanDefinition(String beanName);
    int getBeanDefinitionCount();
    String[] getBeanDefinitionNames();

}
