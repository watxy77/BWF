package com.bwf.core.support;

import com.bwf.core.exception.BeansException;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory{
    @Override
    public <T> T createBean(Class<T> beanClass) throws BeansException {
        return null;
    }

    @Override
    public void autowireBean(Object existingBean) throws BeansException {

    }

    @Override
    public Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException {
        return null;
    }

    @Override
    public Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException {
        return null;
    }

    @Override
    public Object initializeBean(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void destroyBean(Object existingBean) {

    }
}
