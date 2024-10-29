package com.bwf.core.beans.reader;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.BeanNodeEnum;
import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.proxy.groovy.GroovyNodeProxy;
import com.bwf.core.beans.proxy.NodeProxy;
import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    protected final static String charsetName = "UTF-8";
    protected final static String bwf_node = "bwf-node";
    protected ConfigurableListableBeanFactory beanFactory;
    protected String[] beanPathArr;

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    public AbstractBeanDefinitionReader(ConfigurableListableBeanFactory beanFactory, String[] beanPathArr) {
        this.beanFactory = beanFactory;
        this.beanPathArr = beanPathArr;
    }


    @Override
    public String[] getBeanPathArr() {
        return this.beanPathArr;
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
            //构建node 执行调用链
            buildBeanChainHandler(bdd);
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

    private void buildBeanChainHandler(BeanDefinitionDocument bdd){
        if(bdd.getPropertyValue() != null && bdd.getPropertyValue().size() > 0){
            //执行按权重排序
            Collections.sort(bdd.getPropertyValue());
            NodeProxy firstHandler = null;
            NodeProxy nextHandler = null;
            for (PropertyValue propertyValue : bdd.getPropertyValue()) {
                if(BeanNodeEnum.NODE_GROOVY.getCode() == bdd.getBeanType()){
                    NodeProxy nodeProxy = new GroovyNodeProxy(propertyValue);
                    if(nextHandler != null){
                        nextHandler.setNext(nodeProxy);
                    }else{
                        firstHandler = nodeProxy;
                    }
                    nextHandler = nodeProxy;
                    bdd.addNodeChainHandlerList(nodeProxy);
                }
            }
            bdd.setFirstChainHandler(firstHandler);
        }
    }
}
