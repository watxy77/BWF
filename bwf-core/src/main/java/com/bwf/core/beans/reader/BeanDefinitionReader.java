package com.bwf.core.beans.reader;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.Resource;

public interface BeanDefinitionReader {
    @Nullable
    ClassLoader getBeanClassLoader();
    int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;
    int loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

}
