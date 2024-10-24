package com.bwf.core.beans.reader;

public class BeanDefinitionReaderEntity {
    private String node_id;
    private String node_name;
    private int node_type;
    private String node_class;
    private String class_path;
    private String t_class_path;
    private String class_name;
    private String proxy_class;
    private boolean isSingleton;

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public int getNode_type() {
        return node_type;
    }

    public void setNode_type(int node_type) {
        this.node_type = node_type;
    }

    public String getNode_class() {
        return node_class;
    }

    public void setNode_class(String node_class) {
        this.node_class = node_class;
    }

    public String getProxy_class() {
        return proxy_class;
    }

    public void setProxy_class(String proxy_class) {
        this.proxy_class = proxy_class;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public String getT_class_path() {
        return t_class_path;
    }

    public void setT_class_path(String t_class_path) {
        this.t_class_path = t_class_path;
    }

    public String getClass_path() {
        return class_path;
    }

    public void setClass_path(String class_path) {
        this.class_path = class_path;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
