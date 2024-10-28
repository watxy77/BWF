package com.bwf.core.beans.reader;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.AbstractBeanFactory;
import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.exception.BeanDefinitionStoreException;

import java.util.List;

public interface BeanDefinitionReader {
    @Nullable
    ClassLoader getBeanClassLoader();
    int loadBeanDefinitions(List<BeanDefinitionDocument> bdreList, int source) throws BeanDefinitionStoreException;
    int loadBeanDefinitions(BeanDefinitionDocument bdre) throws BeanDefinitionStoreException;
    int loadBeanDefinitions(EncodedResource resource) throws BeanDefinitionStoreException;
    ConfigurableListableBeanFactory getBeanFactory();

}
