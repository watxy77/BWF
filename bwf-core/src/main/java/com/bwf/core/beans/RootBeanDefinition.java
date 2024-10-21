package com.bwf.core.beans;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.factory.BeanDefinition;

public class RootBeanDefinition implements BeanDefinition, Cloneable{
    public static final String SCOPE_DEFAULT = SCOPE_SINGLETON;
    @Nullable
    private volatile Class beanClass;
    @Nullable
    private volatile String className;
    @Nullable
    private volatile String beanName;
    @Nullable
    private String scope = SCOPE_DEFAULT;
    @Nullable
    volatile Class<?> resolvedTargetType;
    private boolean synthetic = false;
    final Object constructorArgumentLock = new Object();

    public RootBeanDefinition(RootBeanDefinition original) {
        if(original != null){
            setBeanClass(original.getBeanClass());
            setBeanClassName(original.getBeanClassName());
            setScope(original.getScope());
            setSynthetic(original.isSynthetic());
        }

    }
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(this.scope);
    }
    public boolean isSynthetic() {
        return this.synthetic;
    }


    @Nullable
    public Class getBeanClass() {
        if (beanClass == null){
            try {
                beanClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return beanClass;
    }

    public void setBeanClass(@Nullable Class beanClass) {
        this.beanClass = beanClass;
    }

    @Nullable
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(@Nullable String beanName) {
        this.beanName = beanName;
    }

    @Nullable
    public String getScope() {
        return scope;
    }

    public void setScope(@Nullable String scope) {
        this.scope = scope;
    }

    public boolean hasBeanClass() {
        return (this.beanClass instanceof Class);
    }

    public void setSynthetic(boolean synthetic) {
        this.synthetic = synthetic;
    }

    @Override
    public String getBeanClassName() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject instanceof Class) {
            return ((Class<?>) beanClassObject).getName();
        }
        else {
            return (String) beanClassObject;
        }
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.className = beanClassName;
    }
}
