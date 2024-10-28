package com.bwf.core.beans.chain;

import com.bwf.core.beans.PropertyValue;

public class GroovyChainHandler implements NodeChainHandler{
    private PropertyValue propertyValue;
    private NodeChainHandler nextHandler;

    public GroovyChainHandler(PropertyValue propertyValue) {
        doInit(propertyValue);
    }
    @Override
    public NodeChainHandler setNext(NodeChainHandler handler) {

        return this.nextHandler = handler;
    }

    @Override
    public void chainHandle(Object object) {
        System.out.println("【groovy】" + propertyValue.getValue());
        if(nextHandler != null){
            nextHandler.chainHandle(object);
        }
    }

    @Override
    public void doInit(PropertyValue propertyValue) {
       this.propertyValue = propertyValue;
    }
}
