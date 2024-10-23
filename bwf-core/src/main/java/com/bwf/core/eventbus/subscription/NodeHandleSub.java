package com.bwf.core.eventbus.subscription;


import com.bwf.core.beans.factory.annotation.BWFAutowired;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.eventbus.IEventSub.IEventSubArgsAndObject;
import com.bwf.core.node.BWFNodeBeanDefinition;

public class NodeHandleSub extends IEventSubArgsAndObject {
    @BWFAutowired
    private BWFEventMessageBus eventMessageBus;
    public NodeHandleSub(Object... objects) {
        super(objects);

    }
    @Override
    public void invoke(Object args) {
        System.out.println("进入NodeHandleSub处理类");
        if(args instanceof BWFNodeBeanDefinition){

        }else{

        }
    }

}
