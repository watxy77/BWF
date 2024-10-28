package com.bwf.core.beans.factory;

import com.bwf.core.beans.BeanDefinition;
import com.bwf.core.beans.reader.BeanDefinitionDocument;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.exception.NoSuchBeanDefinitionException;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(BeanDefinitionDocument bdd)
            throws BeanDefinitionStoreException;
    void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
    boolean containsBeanDefinition(String beanName);
    String[] getBeanDefinitionNames();
}
