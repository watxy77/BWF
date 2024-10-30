package com.bwf.common.annotation.bootstrap;

import com.bwf.common.utils.StringUtil;

public abstract class BWFBeanDefinition {
    private Class clazz;
    private String scope;
    private String className;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isSingleton(){
        boolean flag = false;
        if(StringUtil.isNotEmpty(this.getScope()) && "singleton".equals(this.getScope())){
            flag = true;
        }
        return flag;

    }
    public boolean isPrototype(){
        boolean flag = false;
        if(StringUtil.isNotEmpty(this.getScope()) && "prototype".equals(this.getScope())){
            flag = true;
        }
        return flag;
    }
}
