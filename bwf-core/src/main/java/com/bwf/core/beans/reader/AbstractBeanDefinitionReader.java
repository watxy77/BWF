package com.bwf.core.beans.reader;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.context.BWFNodeBeanContext;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.Resource;
import org.xml.sax.InputSource;

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
    public int loadBeanDefinitions(List<BeanDefinitionDocument> bddList) throws BeanDefinitionStoreException {
        int rCount = 0;
        for (BeanDefinitionDocument beanDefinitionDocument : bddList) {
            rCount += this.loadBeanDefinitions(beanDefinitionDocument);
        }
        return rCount;
    }

    @Override
    public int loadBeanDefinitions(BeanDefinitionDocument bdd) throws BeanDefinitionStoreException {
        return doLoadBeanDefinitions(bdd, null);
    }

    protected int doLoadBeanDefinitions(BeanDefinitionDocument bdd, @Nullable Set<Resource> actualResources){
        int rCount = 1;

        return rCount;
    }
}
