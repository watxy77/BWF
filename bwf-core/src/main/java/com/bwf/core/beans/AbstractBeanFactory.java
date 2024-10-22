package com.bwf.core.beans;

import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.common.utils.StringUtils;
import com.bwf.core.beans.factory.AutowireCapableBeanFactory;
import com.bwf.core.beans.factory.BeanFactory;
import com.bwf.core.beans.support.NullBean;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.exception.BeanCreationException;
import com.bwf.core.exception.BeansException;
import com.bwf.core.beans.singletonBean.DefaultSingletonBeanRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, AutowireCapableBeanFactory {
    private final static String _CONSUMER = "consumer";
    /**是否开启循环依赖，默认开启*/
    private boolean allowCircularReferences = true;
    private volatile boolean hasInstantiationAwareBeanPostProcessors;
    private final ConcurrentMap<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();
    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);
    public void setAllowCircularReferences(boolean allowCircularReferences) {
        this.allowCircularReferences = allowCircularReferences;
    }
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null, false);
    }

    protected <T> T doGetBean(
            String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
            throws BeansException {
        String beanName = transformedBeanName(name);
        Object bean = null;
        // 提前检查在一级缓存中是否存在已注册的单例对象
        Object sharedInstance = getSingleton(beanName);
        // 如果单例bean对象存在，切没有创建bean实例时要使用的参数
        if (sharedInstance != null && args == null) {
            StartupInfoLogger.addBWFComponentBeanMessage("--->--->doGetBean一级缓存中存在beanName："+beanName+"-->引用地址："+sharedInstance);
//            bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
        }else{
            StartupInfoLogger.addBWFComponentBeanMessage("--->--->doGetBean一级缓存中不存在beanName："+beanName+"-->引用地址："+sharedInstance);
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

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public Object createBean(String beanName, RootBeanDefinition mbd) throws BeanCreationException {
        RootBeanDefinition mbdToUse = mbd;
        // 锁定class，，根据设置的class属性或者根据className来解析class
        Class<?> resolvedClass = mbd.getBeanClass();
        // 进行条件筛选，重新复制RootBeanDefinition，并设置BeanClass属性
        if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
            //重新创建一个RootBeanDefinition对象
            mbdToUse = new RootBeanDefinition(mbd);
            // 设置BeanClass属性值
            mbdToUse.setBeanClass(resolvedClass);
        }
        try {
            //实际创建Bean的调用
            Object beanInstance = doCreateBean(beanName, mbd);
            return beanInstance;
        }catch (Exception e){
            throw e;
        }
    }

    protected Object doCreateBean(String beanName, RootBeanDefinition rbd) {
        // Instantiate the bean.
        //这个beanWrapper是用来持有创建出来的bean对象的
        BeanWrapper instanceWrapper = null;
        //获取factoryBean实例缓存
        if (rbd.isSingleton()) {
            //有可能在当前Bean创建之前，就有其他的Bean把当前Bean给创建出来了（比如依赖注入过程时）
            //如果是单例对象，从factoryBean实例缓存中删除当前bean定义信息
            instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
        }
        // 没有则创建实例
        if (instanceWrapper == null) {
            //创建Bean实例 根据执行Bean使用对应策略创建新的实例，
            instanceWrapper = createBeanInstance(beanName, rbd);
        }
        // 从包装类中获取原始bean
        Object bean = instanceWrapper.getWrappedInstance();
        // 获取具体的bean对象的class属性
        Class<?> beanType = instanceWrapper.getWrappedClass();
        // 如果不等于NullBean类型，那么修改目标类型
        if (beanType != NullBean.class) {
            rbd.resolvedTargetType = beanType;
        }
        //判断当前bean是否需要提前曝光，单例&允许循环依赖&当前bean正在创建中，检测环境依赖
        boolean earlySingletonExposure = (rbd.isSingleton() && this.allowCircularReferences &&
                isSingletonCurrentlyInCreation(beanName));
        //是否出现循环依赖
        if (earlySingletonExposure) {
            //循环依赖添加三级缓存
            // 为了避免后期循环依赖，可在bean初始化完成前将创建实例的ObjectFactory加入工厂
            StartupInfoLogger.addBWFComponentBeanMessage("--->--->--->创建bean并添加三级缓存beanName："+beanName+" -->引用地址："+bean);
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, rbd, bean));
        }
        // Initialize the bean instance.
        Object exposedObject = bean;
        try {
            //属性填充，将各个属性值注入，其中，可能存在依赖于其他bean的属性，则会通过递归初始化依赖的bean
            populateBean(beanName, rbd, instanceWrapper);
            //初始化
            exposedObject = initializeBean(beanName, exposedObject, rbd);
        } catch (Throwable ex) {
            throw new BeanCreationException("Initialization of bean failed");
        }
//        StartupInfoLogger.addBWFComponentBeanMessage("--->--->--->开始创建代理对象：" + instanceWrapper);
        return exposedObject;
    }

    /**属性填充*/
    protected void populateBean(String beanName, RootBeanDefinition rbd, @Nullable BeanWrapper bw) {
        if (bw == null) {

        }
        try{
            //获取Bean引用
            Class<?> clazz = rbd.getBeanClass();
            Object instance = rbd.getBeanInstance();
            for(Field field :clazz.getDeclaredFields()){
                //获取属性名称
                String fieldName = field.getName();
                Class<?> type = field.getType();
                if(field.isAnnotationPresent(BWFAutowired.class)){
                    //获取依赖注入需要的bean对象
                    StartupInfoLogger.addBWFComponentBeanMessage("--->--->--->--->开始属性填充beanName："+type.getName()+" -->引用地址：" + instance);
                    Object bean = getBean(type.getName());
                    field.setAccessible(true);
                    field.set(instance, bean);
                    StartupInfoLogger.addBWFComponentBeanMessage("--->--->--->--->--->getBean("+type.getName()+")属性填充fieldName："+fieldName+" -->instance引用：【"+instance+"】" + "填充bean引用：【"+bean+"】");
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    /**初始化bean*/
    protected Object initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition rbd) {
        Object wrappedBean = bean;
//        调用初始化方法
        if(wrappedBean instanceof BWFInitializingBean){
            try {
                ((BWFInitializingBean) wrappedBean).afterPropertiesSet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return wrappedBean;
    }

    protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition rbd) {
        boolean resolved = false;
        boolean autowireNecessary = false;
        //普通对象生成
        BeanWrapper bw = null;
        try {
            Class<?> beanClass = rbd.getBeanClass();
            Object beanInstance = rbd.getBeanInstance();
            bw = new BeanWrapperImpl();
            bw.setWrappedInstance(beanInstance);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return bw;
    }

    protected Object getEarlyBeanReference(String beanName, RootBeanDefinition rbd, Object bean) {
        //原始对象赋值
        Object exposedObject = bean;
        if (!rbd.isSynthetic() && this.hasInstantiationAwareBeanPostProcessors) {
//            for (BeanPostProcessor bp : getBeanPostProcessors()) {
//                if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
//                    SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
//                    exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
//                }
//            }
        }


        // Create proxy if we have advice.
        //判断当前bean是否存在匹配的advice。如果存在生成一个代理对象
        //此处根据以及类中的方法匹配达到Interceptor（也就是Advice），然后生成代理对象，代理对象在执行的时候，还会根据当前
//        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
//        if (specificInterceptors != DO_NOT_PROXY) {
//            this.advisedBeans.put(cacheKey, Boolean.TRUE);
//            Object proxy = createProxy(
//                    bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
//            this.proxyTypes.put(cacheKey, proxy.getClass());
//            return proxy;
//        }
//
//        this.advisedBeans.put(cacheKey, Boolean.FALSE);
//        return bean;
        return exposedObject;
    }



    protected RootBeanDefinition getLocalRootBeanDefinition(String className) throws BeansException {
        String beanName = transformedBeanName(className);
        //如果beanName没有设置,获取名称
        RootBeanDefinition rbd = this.mergedBeanDefinitions.get(beanName);
        if (rbd != null) {
            return rbd;
        }else{

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

    @Override
    public void preInstantiateSingletons(String className, Class<?> clazz) throws BeansException {
        RootBeanDefinition bd = getLocalRootBeanDefinition(className);
        bd.setBeanClass(clazz);
        StartupInfoLogger.addBWFComponentBeanMessage("--->RootBeanDefinition:" + bd);
        //条件判断，单例
        if (bd.isSingleton()) {
            Object bean = getBean(className);

        }else{
            getBean(className);
        }
    }

    @Override
    public void destroyBean(Object existingBean) {

    }

    protected String transformedBeanName(String className) {
        String beanName = "";
        if(StringUtils.isNotEmpty(className)){
            int startIndex = className.lastIndexOf(".");
            beanName = className.substring(startIndex + 1, className.length());
        }
        return beanName;
    }
}
