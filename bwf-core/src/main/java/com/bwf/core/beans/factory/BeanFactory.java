package com.bwf.core.beans.factory;

import com.bwf.core.exception.BeansException;

public interface BeanFactory {
    Object getBean(String name) throws BeansException;
    boolean containsBean(String name);
}
