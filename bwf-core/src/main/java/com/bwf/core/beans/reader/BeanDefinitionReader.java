package com.bwf.core.beans.reader;

import com.alibaba.fastjson.JSONObject;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.Resource;

import java.util.List;

public interface BeanDefinitionReader {
    @Nullable
    ClassLoader getBeanClassLoader();
    int loadBeanDefinitions(List<BeanDefinitionReaderEntity> bdreList) throws BeanDefinitionStoreException;
    int loadBeanDefinitions(BeanDefinitionReaderEntity bdre) throws BeanDefinitionStoreException;

}
