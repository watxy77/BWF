package com.bwf.engine.BWFComponent;

import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.BWFInitializingBean;

@BWFComponent
public class a implements BWFInitializingBean {
    @BWFAutowired
    private b b;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("111");
        System.out.println(b);
    }
}
