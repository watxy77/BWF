package com.bwf.core.eventbus.IEventCallBack;
/**
 * @Author bjweijiannan
 * @description
 */
@FunctionalInterface
public interface EventCallBackArgs extends EventCallBack {
    void invoke(Object args);

}
