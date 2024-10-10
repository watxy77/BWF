package com.bwf.core.eventbus.IEventCallBack;

import com.bwf.core.eventbus.AppletEventMessageBus;

public interface IEventMessageBus {
    AppletEventMessageBus emit(String eventName, Object args);
    AppletEventMessageBus emit(boolean acync, String eventName, Object args);
    boolean checkEvent(String eventName);

    AppletEventMessageBus removeEvent(String eventName, EventCallBack callBack);
    AppletEventMessageBus removeEvent(String eventName);


    AppletEventMessageBus bind(String eventName, EventCallBack eventCallBack);
    AppletEventMessageBus bind(String eventName, EventCallBackNoArgs callBackNoArgs);
    AppletEventMessageBus bind(String eventName, EventCallBackArgs callBackArgs);

    void setPubEvent(String eventName, Object args);

    void setSubEvent(String eventName,  Object object);


}
