package com.bwf.core.beans.factory;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

public interface BeanDefinition {
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    @Nullable
    String getBeanClassName();
    void setBeanClassName(@Nullable String beanClassName);
}
