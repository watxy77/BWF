package com.bwf.core.beans.resource;


import cn.hutool.core.lang.Assert;
import com.bwf.core.beans.BeanDefinition;

import java.io.IOException;
import java.io.InputStream;

public class BeanDefinitionResource extends AbstractResource{
    private final BeanDefinition beanDefinition;

    public BeanDefinitionResource(BeanDefinition beanDefinition) {
        Assert.notNull(beanDefinition, "BeanDefinition must not be null");
        this.beanDefinition = beanDefinition;
    }

    public final BeanDefinition getBeanDefinition() {
        return this.beanDefinition;
    }
    @Override
    public int hashCode() {
        return this.beanDefinition.hashCode();
    }
}
