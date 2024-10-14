package com.bwf.engine.BWFComponent;

import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;

@BWFComponent
public class b  implements BWFInitializingBean {
    @BWFAutowired
    private a a;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(a != null) {
            a.text();
        }
    }

    public void text(){
        System.out.println("bbbbbb------>"+a);
    }
}
