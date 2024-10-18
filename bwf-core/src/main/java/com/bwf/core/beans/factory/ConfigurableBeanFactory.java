package com.bwf.core.beans.factory;

import com.bwf.core.beans.singletonBean.SingletonBeanRegistry;

public interface ConfigurableBeanFactory extends SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
}
