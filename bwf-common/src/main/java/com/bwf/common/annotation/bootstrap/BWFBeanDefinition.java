package com.bwf.common.annotation.bootstrap;

import com.bwf.common.utils.StringUtils;

public abstract class BWFBeanDefinition {
    private Class clazz;
    private String scope;
    private String className;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSingleton(){
        boolean flag = false;
        if(StringUtils.isNotEmpty(this.getScope()) && "singleton".equals(this.getScope())){
            flag = true;
        }
        return flag;

    }
    public boolean isPrototype(){
        boolean flag = false;
        if(StringUtils.isNotEmpty(this.getScope()) && "prototype".equals(this.getScope())){
            flag = true;
        }
        return flag;
    }
}
