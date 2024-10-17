package com.bwf.core.support;

import com.bwf.core.exception.BeansException;

public interface AutowireCapableBeanFactory {
    <T> T createBean(Class<T> beanClass) throws BeansException;
    void autowireBean(Object existingBean) throws BeansException;
    Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;
    Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException;
    Object initializeBean(Object existingBean, String beanName) throws BeansException;
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;
    void destroyBean(Object existingBean);
}
