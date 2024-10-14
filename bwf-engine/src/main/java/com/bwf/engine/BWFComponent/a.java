package com.bwf.engine.BWFComponent;

import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.eventbus.model.EventEnum;
import com.bwf.core.eventbus.subscription.DataHandleSub;

@BWFComponent
public class a implements BWFInitializingBean {
    @BWFAutowired
    private b b;
    @BWFAutowired
    private BWFEventMessageBus eventMessageBus;
    @Override
    public void afterPropertiesSet() throws Exception {
        if (b != null) {
            b.text();
            System.out.println("eventMessageBus----->"+eventMessageBus);
        }
    }
    public void text(){
        System.out.println("----->aaaaa------>"+b);
        eventMessageBus.setPubEvent(EventEnum.DUCC_HANDLE_SUB.getCode(), new DataHandleSub());
    }
}
