package com.bwf.core.eventbus.IEventCallBack;
/**
 * @Author bjweijiannan
 * @description
 */
@FunctionalInterface
public interface EventCallBackObject extends EventCallBack {
    void invoke(Object args);

}
