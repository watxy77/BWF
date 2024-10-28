package com.bwf.core.proxy;

import com.bwf.common.annotation.bootstrap.BWFBeanDefinition;

public class BWFNodeBeanDefinition extends BWFBeanDefinition {
    private int type;
    private String param;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
