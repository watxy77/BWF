package com.bwf.core.eventbus.subscription;


import com.bwf.core.eventbus.IEventSub.IEventSubArgsAndObject;
import groovy.lang.GroovyObject;

import java.util.List;

public class DataHandleSub extends IEventSubArgsAndObject {

    public DataHandleSub(Object... objects) {
        super(objects);

    }
    @Override
    public void invoke(Object args) {
        System.out.println("进入DataHandle处理类");
    }

}
