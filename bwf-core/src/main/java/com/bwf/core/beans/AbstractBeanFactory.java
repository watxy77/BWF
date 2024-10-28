package com.bwf.core.beans;

import com.bwf.core.beans.factory.BeanDefinitionRegistry;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.factory.BeanFactory;
import com.bwf.core.beans.reader.BeanDefinitionDocument;
import com.bwf.core.beans.utils.BeanUtil;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.exception.BeansException;
import com.bwf.core.beans.singletonBean.DefaultSingletonBeanRegistry;
import com.bwf.core.exception.NoSuchBeanDefinitionException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    /**是否开启循环依赖，默认开启*/
    private boolean allowCircularReferences = true;
    private final ConcurrentMap<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();
    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);
    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanDefinition(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return containsBeanDefinition(name);
    }

    @Override
    public void registerBeanDefinition(BeanDefinitionDocument bdd) throws BeanDefinitionStoreException {
        RootBeanDefinition bd = getLocalRootBeanDefinition(bdd.getClassName());
//        bd.setBeanClass(clazz);
//        StartupInfoLogger.addLoadBeanMessage("--->RootBeanDefinition:" + bd);
//        //条件判断，单例
//        if (bd.isSingleton()) {
//            Object bean = getBean(className);
//
//        }else{
//            getBean(className);
//        }
    }

    @Override
    public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
       return doGetBean(beanName, null, null, false);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    protected <T> T doGetBean(
            String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
            throws BeansException {
        String beanName = BeanUtil.transformedBeanName(name);
        Object bean = null;
        // 提前检查在一级缓存中是否存在已注册的单例对象
        Object sharedInstance = getSingleton(beanName);
        // 如果单例bean对象存在，切没有创建bean实例时要使用的参数
        if (sharedInstance != null && args == null) {
            StartupInfoLogger.addLoadBeanMessage("--->--->doGetBean一级缓存中存在beanName："+beanName+"-->引用地址："+sharedInstance);
//            bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
        }else{
            StartupInfoLogger.addLoadBeanMessage("--->--->doGetBean一级缓存中不存在beanName："+beanName+"-->引用地址："+sharedInstance);
            RootBeanDefinition mbd = getLocalRootBeanDefinition(name);
            mbd.setBeanClassName(name);
            if (mbd.isSingleton()) {
                sharedInstance = getSingleton(beanName, () -> {
                    try {
                        return createBean(beanName, mbd);
                    }catch (Exception e) {
                        destroyBean(beanName);
                        throw e;
                    }
                });
            }
        }
        return (T) sharedInstance;
    }

    protected RootBeanDefinition getLocalRootBeanDefinition(String className) throws BeansException {
        String beanName = BeanUtil.transformedBeanName(className);
        //如果beanName没有设置,获取名称
        RootBeanDefinition rbd = this.mergedBeanDefinitions.get(beanName);
        if (rbd != null) {
            return rbd;
        }
        return getLocalRootBeanDefinition(className, beanName, rbd);
    }

    protected RootBeanDefinition getLocalRootBeanDefinition(String className, String beanName, RootBeanDefinition rbd)
            throws BeansException {
        synchronized (this.mergedBeanDefinitions) {
            RootBeanDefinition mbd = null;
//            RootBeanDefinition previous = null;
            //再次获取缓存对象中的beanName
            if (rbd == null) {
                mbd = this.mergedBeanDefinitions.get(beanName);
            }
            try{
                if (mbd == null) {
                    mbd = new RootBeanDefinition(mbd);
//                previous = mbd;
                    mbd.setBeanName(beanName);
                    mbd.setBeanClassName(className);
                    mbd.setBeanInstance(mbd.getBeanClass().newInstance());
                    this.mergedBeanDefinitions.put(beanName, mbd);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return mbd;
        }
    }
}
