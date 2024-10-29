package com.bwf.core.beans.proxy.aviator;

import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.proxy.NodeProxy;
import com.googlecode.aviator.AviatorEvaluator;

public class AviatorNodeProxy implements NodeProxy {
    private PropertyValue propertyValue;
    private NodeProxy nextHandler;

    public AviatorNodeProxy(PropertyValue propertyValue) {
        doInit(propertyValue);
    }
    @Override
    public NodeProxy setNext(NodeProxy handler) {
        return this.nextHandler = handler;
    }

    @Override
    public void invoke(Object object) {
        System.out.println("【aviator】" + propertyValue.getValue());
        AviatorEvaluator.execute("1+1");
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
