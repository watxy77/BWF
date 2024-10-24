package com.bwf.core.beans.reader;

import com.alibaba.fastjson.JSONObject;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.annotation.BWFNode;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.context.BWFNodeBeanContext;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.FileUtil;
import com.bwf.core.io.Resource;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    protected final static String charsetName = "UTF-8";
    protected final static String bwf_node = "bwf-node";
    protected BWFNodeBeanContext bwfNodeBeanContext;
    @Nullable
    private ClassLoader beanClassLoader;

    public AbstractBeanDefinitionReader(BWFNodeBeanContext bwfNodeBeanContext) {
        this.bwfNodeBeanContext = bwfNodeBeanContext;
    }

    public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }
    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public int loadBeanDefinitions(List<BeanDefinitionReaderEntity> bdreList) throws BeanDefinitionStoreException {
        int rCount = 0;
        for (BeanDefinitionReaderEntity beanDefinitionReaderEntity : bdreList) {
            rCount += this.loadBeanDefinitions(beanDefinitionReaderEntity);
        }
        return rCount;
    }

    @Override
    public int loadBeanDefinitions(BeanDefinitionReaderEntity bdre) throws BeanDefinitionStoreException {
        return loadBeanDefinitions(bdre, null);
    }

    public int loadBeanDefinitions(BeanDefinitionReaderEntity bdre, @Nullable Set<Resource> actualResources) throws BeanDefinitionStoreException {
        int rCount = 1;
        // 处理beanNode加载
        return rCount;
    }
}
