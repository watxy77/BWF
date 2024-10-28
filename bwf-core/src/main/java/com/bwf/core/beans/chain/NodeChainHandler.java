package com.bwf.core.beans.chain;

import com.bwf.core.beans.PropertyValue;

public interface NodeChainHandler {

    NodeChainHandler setNext(NodeChainHandler handler);

    void chainHandle(Object object);

    void doInit(PropertyValue propertyValue);

}
