package com.bwf.core.eventbus;

import com.bwf.core.eventbus.IEventCallBack.*;
import com.bwf.core.eventbus.IEventSub.IEventSub;
import com.bwf.core.eventbus.IEventSub.IEventSubArgs;
import com.bwf.core.eventbus.IEventSub.IEventSubArgsAndObject;
import com.bwf.core.eventbus.IEventSub.IEventSubObject;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppletEventMessageBus implements IEventMessageBus {
    private final ConcurrentHashMap<String, List<EventCallBack>> appletEventBus = new ConcurrentHashMap<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private volatile static AppletEventMessageBus appletEventMessageBus;

    public static AppletEventMessageBus getInstance() {
        if (null == appletEventMessageBus) {
            synchronized (AppletEventMessageBus.class) {
                if (null == appletEventMessageBus) {
                    appletEventMessageBus = new AppletEventMessageBus();
                }
            }
        }
        return appletEventMessageBus;
    }

    @Override
    public AppletEventMessageBus emit(String eventName, Object args) {

        return this.emit(true, eventName, args);
    }

    @Override
    public AppletEventMessageBus emit(boolean acync, String eventName, Object args) {
        if(!checkEvent(eventName)){
            return this;
        }

        List<EventCallBack> eventCallBackList = appletEventBus.get(eventName);
        if (eventCallBackList.size() == 0){
            return this;
        }else{
            Runnable task = () -> {
                eventCallBackList.forEach(eventCallBack -> {
                    if(eventCallBack instanceof EventCallBackArgs){
                        EventCallBackArgs callBackArgs = (EventCallBackArgs) eventCallBack;
                        callBackArgs.invoke(args);
                        return;
                    }else if(eventCallBack instanceof EventCallBackObject){
                        EventCallBackObject callBackArgs = (EventCallBackObject) eventCallBack;
                        callBackArgs.invoke(args);
                        return;
                    }else{
                        ((EventCallBackNoArgs) eventCallBack).invoke();
                    }


                });
            };
            if(acync){
                task.run();
                return this;
            }else {
                threadPool.execute(task);
            }
        }

        return this;
    }


    @Override
    public boolean checkEvent(String eventName) {
        if(!appletEventBus.containsKey(eventName)){
            appletEventBus.put(eventName, new LinkedList<>());
            return false;
        }
        return true;
    }

    @Override
    public AppletEventMessageBus removeEvent(String eventName, EventCallBack callBack) {
        if(!appletEventBus.containsKey(eventName)){
            return this;
        }

        List<EventCallBack> eventCallBacks = appletEventBus.get(eventName);
        eventCallBacks.remove(callBack);
        return this;
    }

    @Override
    public AppletEventMessageBus removeEvent(String eventName) {
        if(!appletEventBus.containsKey(eventName)){
            return this;
        }
        appletEventBus.remove(eventName);
        return null;
    }

    @Override
    public AppletEventMessageBus bind(String eventName, EventCallBack eventCallBack) {
        if(checkEvent(eventName)){
            return this;
        }

        List<EventCallBack> eventCallBackList = appletEventBus.get(eventName);
        eventCallBackList.add(eventCallBack);
        return this;
    }

    @Override
    public AppletEventMessageBus bind(String eventName, EventCallBackNoArgs callBackNoArgs) {
        return bind(eventName, (EventCallBack) callBackNoArgs);
    }

    @Override
    public AppletEventMessageBus bind(String eventName, EventCallBackArgs callBackArgs) {
        return bind(eventName, (EventCallBack) callBackArgs);
    }

    @Override
    public void setPubEvent(String eventName, Object object) {
        if (object instanceof IEventSubArgs){
            this.appletEventMessageBus.bind(eventName, (IEventSubArgs)object);
        }else if (object instanceof IEventSub){
            this.appletEventMessageBus.bind(eventName, (IEventSub)object);
        }else if (object instanceof IEventSubObject){
            this.appletEventMessageBus.bind(eventName, (IEventSubObject)object);
        }else if (object instanceof IEventSubArgsAndObject){
            this.appletEventMessageBus.bind(eventName, (IEventSubArgsAndObject)object);
        }else{
        }
    }

    @Override
    public void setSubEvent(String eventName, Object object) {
        this.appletEventMessageBus.emit(eventName, object);

    }


    public static void main(String[] args) {
        AppletEventMessageBus instance = AppletEventMessageBus.getInstance();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int msg=0;
//                while (true){
//
//                    AppletEventEmitter hello = instance.emit(true, "hello", "你好" + msg);
//                    try {
//                        msg++;
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int msg=0;
//                while (true){
//
//                    instance.emit(true, "akka", "akka"+msg);
//
//                    try {
//                        msg++;
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

        instance.bind("hello", new EventCallBackArgs() {
            @Override
            public void invoke(Object args) {
                System.out.println("收到消息："+ args);
            }
        }).bind("akka", new EventCallBackArgs() {
            @Override
            public void invoke(Object args) {
                System.out.println("收到消息："+ args);
            }
        }).bind("akka1", new EventCallBackArgs() {
            @Override
            public void invoke(Object args) {
                System.out.println("收到消息1111："+ args);
            }
        });

        AppletEventMessageBus hello = instance.emit(true, "hello", "你好");

    }
}
