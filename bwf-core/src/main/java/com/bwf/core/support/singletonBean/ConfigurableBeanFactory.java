package com.bwf.core.support.singletonBean;

public interface ConfigurableBeanFactory extends SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
}
