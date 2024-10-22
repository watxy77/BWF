package com.bwf.engine.BWFComponent;

import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.BWFInitializingBean;

@BWFComponent
public class A implements BWFInitializingBean {
    @BWFAutowired
    private B b;
//    @BWFAutowired
//    private BWFEventMessageBus eventMessageBus;
    @Override
    public void afterPropertiesSet() throws Exception {
        if(b != null){
            b.text();
//            System.out.println("eventMessageBus----->"+eventMessageBus);
        }else {
            System.out.println("b属性为null");
        }
    }
    public void text(){
        System.out.println("----->aaaaa------>"+ b);
//        eventMessageBus.setPubEvent(EventEnum.DUCC_HANDLE_SUB.getCode(), new DataHandleSub());
    }
}
