package com.bwf.core.eventbus.IEventCallBack;

import com.bwf.core.eventbus.BWFEventMessageBus;
/**
 * @Author bjweijiannan
 * @description
 */
public interface IEventMessageBus {
    BWFEventMessageBus emit(String eventName, Object args);
    BWFEventMessageBus emit(boolean acync, String eventName, Object args);
    boolean checkEvent(String eventName);

    BWFEventMessageBus removeEvent(String eventName, EventCallBack callBack);
    BWFEventMessageBus removeEvent(String eventName);


    BWFEventMessageBus bind(String eventName, EventCallBack eventCallBack);
    BWFEventMessageBus bind(String eventName, EventCallBackNoArgs callBackNoArgs);
    BWFEventMessageBus bind(String eventName, EventCallBackArgs callBackArgs);

    void setPubEvent(String eventName, Object args);

    void setSubEvent(String eventName,  Object object);


}
