package com.bwf.core.beans;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.factory.AutowireCapableBeanFactory;
import com.bwf.core.beans.factory.BeanFactory;
import com.bwf.core.exception.BeanCreationException;
import com.bwf.core.exception.BeansException;
import com.bwf.core.beans.singletonBean.DefaultSingletonBeanRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, AutowireCapableBeanFactory {
    private final static String _CONSUMER = "consumer";
    /**是否开启循环依赖，默认开启*/
    private boolean allowCircularReferences = true;
    private final ConcurrentMap<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();
    public void setAllowCircularReferences(boolean allowCircularReferences) {
        this.allowCircularReferences = allowCircularReferences;
    }
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null, false);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return doGetBean(name, requiredType, null, false);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, null, args, false);
    }

    protected <T> T doGetBean(
            String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
            throws BeansException {
        String beanName = transformedBeanName(name);
        Object bean = null;

        Object sharedInstance = getSingleton(beanName);
        return (T) bean;
    }

    protected String transformedBeanName(String name) {
       return "";
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public Object createBean(String className, Class beanClass) throws BeanCreationException {
        RootBeanDefinition rbd = new RootBeanDefinition();
        rbd.setBeanName(className);
        rbd.setBeanClass(beanClass);

        doCreateBean(className, rbd);
        return null;
    }

    protected Object doCreateBean(String beanName, RootBeanDefinition rbd) {
        // Instantiate the bean.
        BeanWrapper instanceWrapper = null;

        if (rbd.isSingleton()) {
            //有可能在本Bean创建之前，就有其他的Bean把当前Bean给创建出来了（比如依赖注入过程时）
            instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
        }
        if (instanceWrapper == null) {
            //创建Bean实例
            instanceWrapper = createBeanInstance(beanName, rbd);
        }
        Object bean = instanceWrapper.getWrappedInstance();
        //判断循环依赖
        boolean earlySingletonExposure = (rbd.isSingleton() && this.allowCircularReferences &&
                isSingletonCurrentlyInCreation(beanName));
        //是否出现循环依赖
        if (earlySingletonExposure) {
            //循环依赖添加三级缓存
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, rbd, bean));
        }
        // Initialize the bean instance.
        Object exposedObject = bean;
        try {
            //属性填充
            populateBean(beanName, rbd, instanceWrapper);
            //初始化
            exposedObject = initializeBean(beanName, exposedObject, rbd);
        } catch (Throwable ex) {
            throw new BeanCreationException("Initialization of bean failed");
        }
        return null;
    }

    /**属性填充*/
    protected void populateBean(String beanName, RootBeanDefinition rbd, @Nullable BeanWrapper bw) {
        if (bw == null) {

        }
    }

    /**属性填充*/
    protected Object initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition rbd) {
        Object wrappedBean = bean;

        return wrappedBean;
    }

    protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition rbd) {
        boolean resolved = false;
        boolean autowireNecessary = false;
        //普通对象生成
        BeanWrapper bw = null;
        try {
            Class beanClass = rbd.getBeanClass();
            Object beanInstance = beanClass.getConstructor().newInstance();
            bw = new BeanWrapperImpl();
            bw.setWrappedInstance(beanInstance);

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return bw;
    }

    protected Object getEarlyBeanReference(String beanName, RootBeanDefinition rbd, Object bean) {
        Object exposedObject = bean;
//        if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
//            for (BeanPostProcessor bp : getBeanPostProcessors()) {
//                if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
//                    SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
//                    exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
//                }
//            }
//        }
        return exposedObject;
    }


    @Override
    public void destroyBean(Object existingBean) {

    }
}
