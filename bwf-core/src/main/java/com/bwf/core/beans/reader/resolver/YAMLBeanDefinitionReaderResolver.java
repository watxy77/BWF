package com.bwf.core.beans.reader.resolver;

import com.bwf.core.beans.reader.AbstractBeanDefinitionReader;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.Resource;

public class YAMLBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {
    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        return 0;
    }
}
