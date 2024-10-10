package com.bwf.core.eventbus.IEventCallBack;

@FunctionalInterface
public interface EventCallBackNoArgs extends EventCallBack {
    void invoke();

}
