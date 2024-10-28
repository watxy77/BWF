package com.bwf.core.beans.reader;

import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.chain.NodeChainHandler;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinitionDocument {
    private String beanId;
    private String beanName;
    private int beanType;
    private int source;
    private String classPath;
    private String tClassPath;
    private String className;
    private String proxyClass;
    private boolean isSingleton;
    private List<PropertyValue> propertyValue;
    private NodeChainHandler firstChainHandler;
    private List<NodeChainHandler> nodeChainHandlerList;



    public BeanDefinitionDocument() {
        nodeChainHandlerList = new ArrayList<>();
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

    public String gettClassPath() {
        return tClassPath;
    }

    public void settClassPath(String tClassPath) {
        this.tClassPath = tClassPath;
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

    public NodeChainHandler getFirstChainHandler() {
        return firstChainHandler;
    }

    public void setFirstChainHandler(NodeChainHandler firstChainHandler) {
        this.firstChainHandler = firstChainHandler;
    }

    public List<NodeChainHandler> getNodeChainHandlerList() {
        return nodeChainHandlerList;
    }

    public void addNodeChainHandlerList(NodeChainHandler nodeChainHandler) {
        this.nodeChainHandlerList.add(nodeChainHandler);
    }
}
