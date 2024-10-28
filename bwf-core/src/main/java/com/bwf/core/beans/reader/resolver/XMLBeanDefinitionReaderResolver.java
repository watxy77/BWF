package com.bwf.core.beans.reader.resolver;

import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.beans.reader.AbstractBeanDefinitionReader;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.beans.BWFNodeBeanFactory;
import com.bwf.core.exception.BeanDefinitionStoreException;

public class XMLBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {

    public XMLBeanDefinitionReaderResolver(ConfigurableListableBeanFactory beanFactory) {
        super(beanFactory);
    }


    @Override
    public int loadBeanDefinitions(EncodedResource resource) throws BeanDefinitionStoreException {

        for (String path : resource.getPath()) {

        }

        return 0;
    }
}
