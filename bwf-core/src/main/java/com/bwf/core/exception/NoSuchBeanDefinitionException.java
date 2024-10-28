package com.bwf.core.exception;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

public class NoSuchBeanDefinitionException extends BeansException {
    @Nullable
    private final String beanName;
    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' available");
        this.beanName = name;
    }

    public NoSuchBeanDefinitionException(String name, String message) {
        super("No bean named '" + name + "' available: " + message);
        this.beanName = name;
    }
}
