package com.bwf.core.beans.reader;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.AbstractBeanFactory;
import com.bwf.core.beans.BWFNodeBeanFactory;
import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.Resource;

import java.util.List;
import java.util.Set;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    protected final static String charsetName = "UTF-8";
    protected final static String bwf_node = "bwf-node";
    protected ConfigurableListableBeanFactory beanFactory;
    @Nullable
    private ClassLoader beanClassLoader;

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public AbstractBeanDefinitionReader(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public int loadBeanDefinitions(List<BeanDefinitionDocument> bddList, int source) throws BeanDefinitionStoreException {
        int rCount = 0;
        for (BeanDefinitionDocument beanDefinitionDocument : bddList) {
            beanDefinitionDocument.setSource(source);
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
        // 判断bean对象名称是否被占用
        if(!getBeanFactory().isBeanNameInUse(bdd.getBeanName())){
            // 继续创建Bean对象
            getBeanFactory().registerSingleton(bdd);
        }else{
            throw new BeanDefinitionStoreException("【"+(bdd.getBeanName()+"】IOC容器中已经创建过该bean，无需创建"));
        }
//        this.bwfNodeBeanFactory.
//        BeanDefinitionDocumentReader documentReader = createBeanDefinitionDocumentReader();
//        int countBefore = getRegistry().getBeanDefinitionCount();
//        documentReader.registerBeanDefinitions(doc, createReaderContext(resource));
//        return getRegistry().getBeanDefinitionCount() - countBefore;
        return rCount;
    }
}
