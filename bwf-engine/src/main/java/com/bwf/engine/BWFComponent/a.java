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
        if (b != null) {
            b.text();
        }
    }
    public void text(){
        System.out.println("----->aaaaa------>"+b);
    }
}
