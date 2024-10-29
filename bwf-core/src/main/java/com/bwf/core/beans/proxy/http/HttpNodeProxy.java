package com.bwf.core.beans.proxy.http;

import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.proxy.NodeProxy;

public class HttpNodeProxy implements NodeProxy {
    private PropertyValue propertyValue;
    private NodeProxy nextHandler;

    public HttpNodeProxy(PropertyValue propertyValue) {
        doInit(propertyValue);
    }
    @Override
    public NodeProxy setNext(NodeProxy handler) {
        return this.nextHandler = handler;
    }

    @Override
    public void invoke(Object object) {
        System.out.println("【http】" + propertyValue.getValue());
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
