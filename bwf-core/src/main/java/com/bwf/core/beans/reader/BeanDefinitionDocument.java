package com.bwf.core.beans.reader;

import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.proxy.NodeProxy;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinitionDocument {
    private String beanId;
    private String beanName;
    private int beanType;
    private int source;
    private String classPath;
    private String className;
    private String proxyClass;
    private boolean isSingleton;
    private List<PropertyValue> propertyValue;
    private NodeProxy firstChainHandler;
    private List<NodeProxy> nodeProxyList;



    public BeanDefinitionDocument() {
        nodeProxyList = new ArrayList<>();
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public int getBeanType() {
        return beanType;
    }

    public void setBeanType(int beanType) {
        this.beanType = beanType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProxyClass() {
        return proxyClass;
    }

    public void setProxyClass(String proxyClass) {
        this.proxyClass = proxyClass;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public List<PropertyValue> getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(List<PropertyValue> propertyValue) {
        this.propertyValue = propertyValue;
    }

    public NodeProxy getFirstChainHandler() {
        return firstChainHandler;
    }

    public void setFirstChainHandler(NodeProxy firstChainHandler) {
        this.firstChainHandler = firstChainHandler;
    }

    public List<NodeProxy> getNodeChainHandlerList() {
        return nodeProxyList;
    }

    public void addNodeChainHandlerList(NodeProxy nodeProxy) {
        this.nodeProxyList.add(nodeProxy);
    }
}
