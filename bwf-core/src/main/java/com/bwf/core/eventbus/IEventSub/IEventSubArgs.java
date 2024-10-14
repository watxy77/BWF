package com.bwf.core.eventbus.IEventSub;


import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.eventbus.IEventCallBack.EventCallBackArgs;
/**
 * @Author bjweijiannan
 * @description
 */
public abstract class IEventSubArgs implements EventCallBackArgs {
    private BWFEventMessageBus eventMessageBusInstance;

    public IEventSubArgs(BWFEventMessageBus eventMessageBusInstance) {
        this.eventMessageBusInstance = eventMessageBusInstance;
    }
}
