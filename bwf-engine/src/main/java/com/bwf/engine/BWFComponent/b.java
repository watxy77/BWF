package com.bwf.engine.BWFComponent;

import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.core.eventbus.BWFEventMessageBus;

@BWFComponent
public class b  implements BWFInitializingBean {
    @BWFAutowired
    private a a;
    @BWFAutowired
    private BWFEventMessageBus eventMessageBus;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(a != null){
            a.text();
            System.out.println("eventMessageBus------>" + eventMessageBus);
        }
    }

    public void text(){
        System.out.println("bbbbbb------>"+a);
    }
}
