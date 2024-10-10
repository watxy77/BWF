package com.bwf.core.eventbus.IEventCallBack;
/**
 * @Author bjweijiannan
 * @description
 */
@FunctionalInterface
public interface EventCallBackNoArgs extends EventCallBack {
    void invoke();

}
