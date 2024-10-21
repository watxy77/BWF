package com.bwf.core.beans;

public class BeanWrapperImpl implements BeanWrapper{
    private Class clazz;
    private Object wrappedInstance;

    public BeanWrapperImpl() {
    }

    @Override
    public void setWrappedInstance(Object o) {
        this.wrappedInstance = o;
    }

    @Override
    public Object getWrappedInstance() {
        return this.wrappedInstance;
    }

    @Override
    public Class<?> getWrappedClass() {
        this.clazz = getWrappedInstance().getClass();
        return  this.clazz;
    }
}
