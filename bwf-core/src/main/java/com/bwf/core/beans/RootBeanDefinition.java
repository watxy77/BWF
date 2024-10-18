package com.bwf.core.beans;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.factory.BeanDefinition;

public class RootBeanDefinition implements BeanDefinition, Cloneable{
    public static final String SCOPE_DEFAULT = SCOPE_SINGLETON;
    @Nullable
    private volatile Class beanClass;
    @Nullable
    private volatile String beanName;
    @Nullable
    private String scope = SCOPE_DEFAULT;
    final Object constructorArgumentLock = new Object();
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(this.scope);
    }


    @Nullable
    public Class getBeanClass() {
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
}
