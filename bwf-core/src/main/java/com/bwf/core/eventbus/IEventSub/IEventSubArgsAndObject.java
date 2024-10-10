package com.bwf.core.eventbus.IEventSub;

import com.bwf.core.eventbus.IEventCallBack.EventCallBackArgs;

public abstract class IEventSubArgsAndObject implements EventCallBackArgs {
    private Object objects;

    public IEventSubArgsAndObject(Object... objects) {
        this.objects = objects;
    }

}
