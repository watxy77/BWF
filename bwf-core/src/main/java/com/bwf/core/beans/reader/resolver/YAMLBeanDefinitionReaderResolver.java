package com.bwf.core.beans.reader.resolver;

import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.beans.reader.AbstractBeanDefinitionReader;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.beans.BWFNodeBeanFactory;
import com.bwf.core.exception.BeanDefinitionStoreException;

public class YAMLBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {

    public YAMLBeanDefinitionReaderResolver(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public int loadBeanDefinitions(EncodedResource resource) throws BeanDefinitionStoreException {
        return 0;
    }
}
