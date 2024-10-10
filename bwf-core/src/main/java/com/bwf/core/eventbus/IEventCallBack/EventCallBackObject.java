package com.bwf.core.eventbus.IEventCallBack;

@FunctionalInterface
public interface EventCallBackObject extends EventCallBack {
    void invoke(Object args);

}
