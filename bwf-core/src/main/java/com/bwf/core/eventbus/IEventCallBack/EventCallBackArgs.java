package com.bwf.core.eventbus.IEventCallBack;

@FunctionalInterface
public interface EventCallBackArgs extends EventCallBack {
    void invoke(Object args);

}
