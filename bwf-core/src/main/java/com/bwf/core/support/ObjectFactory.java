package com.bwf.core.support;

import com.bwf.core.exception.BeansException;

public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
