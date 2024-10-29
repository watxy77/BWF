package com.bwf.core.beans.proxy;

import com.bwf.core.beans.PropertyValue;

public interface NodeProxy {

    NodeProxy setNext(NodeProxy handler);
    void invoke(Object object);
    void doInit(PropertyValue propertyValue);
//    void addMethod(Object obj, String MethodName );
    void updatePV(PropertyValue propertyValue);
}
