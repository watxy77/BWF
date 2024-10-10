package com.bwf.core.eventbus.IEventSub;


import com.bwf.core.eventbus.AppletEventMessageBus;
import com.bwf.core.eventbus.IEventCallBack.EventCallBackArgs;
/**
 * @Author bjweijiannan
 * @description
 */
public abstract class IEventSubArgs implements EventCallBackArgs {
    private AppletEventMessageBus eventMessageBusInstance;

    public IEventSubArgs(AppletEventMessageBus eventMessageBusInstance) {
        this.eventMessageBusInstance = eventMessageBusInstance;
    }
}
