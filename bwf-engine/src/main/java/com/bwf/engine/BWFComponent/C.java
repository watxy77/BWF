package com.bwf.engine.BWFComponent;

import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.core.beans.factory.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.core.eventbus.BWFEventMessageBus;

@BWFComponent
public class C implements BWFInitializingBean {
    @BWFAutowired
    private A a;
    @BWFAutowired
    private BWFEventMessageBus eventMessageBus;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(a != null){
            a.text();
            System.out.println("eventMessageBus------>" + eventMessageBus);
        }else {
            System.out.println("a属性为null");
        }
    }

    public void text(){
        System.out.println("ccccc------>"+a);
    }
}
