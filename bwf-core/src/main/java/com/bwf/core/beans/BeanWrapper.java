package com.bwf.core.beans;

public interface BeanWrapper {
    void setWrappedInstance(Object o);
    Object getWrappedInstance();
    Class<?> getWrappedClass();
}
