package com.bwf.core.beans.proxy.ai;

import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.proxy.NodeProxy;

public class AINodeProxy implements NodeProxy {
    private PropertyValue propertyValue;
    private NodeProxy nextHandler;

    public AINodeProxy(PropertyValue propertyValue) {
        doInit(propertyValue);
    }
    @Override
    public NodeProxy setNext(NodeProxy handler) {
        return this.nextHandler = handler;
    }

    @Override
    public void invoke(Object object) {
        System.out.println("【ai】" + propertyValue.getValue());
        if(nextHandler != null){
            nextHandler.invoke(object);
        }
    }

    @Override
    public void doInit(PropertyValue propertyValue) {
       this.propertyValue = propertyValue;
    }

    @Override
    public void updatePV(PropertyValue propertyValue) {

    }
}