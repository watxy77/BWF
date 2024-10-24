package com.bwf.core.beans.reader.resolver;

import com.alibaba.fastjson.JSONObject;
import com.bwf.core.beans.BeanDefinition;
import com.bwf.core.beans.reader.AbstractBeanDefinitionReader;
import com.bwf.core.beans.resource.BeanDefinitionResource;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.context.BWFNodeBeanContext;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.Resource;

public class XMLBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {

    public XMLBeanDefinitionReaderResolver(BWFNodeBeanContext bwfNodeBeanContext) {
        super(bwfNodeBeanContext);
    }

    public int loadBeanDefinitions(EncodedResource resource) throws BeanDefinitionStoreException {

        for (String path : resource.getPath()) {

        }

        return 0;
    }
}
