package com.bwf.core.beans;

import com.bwf.core.exception.BeansException;

public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
