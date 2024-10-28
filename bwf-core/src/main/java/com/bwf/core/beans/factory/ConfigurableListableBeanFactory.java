package com.bwf.core.beans.factory;

import com.bwf.core.beans.BeanDefinition;
import com.bwf.core.beans.reader.BeanDefinitionDocument;
import com.bwf.core.exception.NoSuchBeanDefinitionException;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    boolean isBeanNameInUse(String beanName);


}
