package com.bwf.core.context;

import com.bwf.common.annotation.bootstrap.annotation.*;
import com.bwf.core.beans.BWFComponentBeanFactory;
import com.bwf.core.beans.BWFNodeBeanFactory;
import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.bootstrap.utils.ClassUtils;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.eventbus.model.EventEnum;
import com.bwf.core.eventbus.subscription.NodeHandleSub;
import com.bwf.core.exception.BeansException;
import com.bwf.core.io.ResourceLoader;
import org.apache.tools.ant.util.ClasspathUtils;

import java.time.Duration;

/**
 * @Author bjweijiannan
 * @description
 */
public class BWFApplicationContext extends AbstractApplicationContext{
    private boolean registerShutdownHook = true;
    private static BWFEventMessageBus eventMessageBusInstance;
    private BWFComponentBeanFactory bwfComponentBeanFactory;
    private BWFNodeBeanFactory nodeBeanFactory;

    public BWFApplicationContext(Class<?>... primarySources) {
        this((ResourceLoader)null, primarySources);
    }
    public BWFApplicationContext(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.mainApplicationClass = ClassUtils.deduceMainApplicationClass();
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return (new BWFApplicationContext(primarySources)).run(args);
    }
    public ConfigurableApplicationContext run(String... args) {
        try{
            //解析注解-BWFApplication
            BWFApplication declaredAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFApplication.class);
            if(declaredAnnotation != null){
                if (this.registerShutdownHook) {
//                    shutdownHook.registerApplicationContext(context);
                }
                super.refresh();

                /**打印启动日志*/
                System.out.println(StartupInfoLogger.lodeBeanMessage());
            }else{
                throw new NullPointerException("未发现BWFApplication注解类，无法初始化BWF框架。请在main启动函数类上添加！");
            }

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            //启动所需时间
            Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - this.startupDate);
            (new StartupInfoLogger(this.mainApplicationClass)).logStarted(timeTakenToStartup);
        }

        return this;
    }


    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.nodeBeanFactory;
    }

    @Override
    protected void refreshBeanFactory() throws BeansException, IllegalStateException {
        //创建BeanFactory对象
//        this.bwfComponentBeanFactory = new BWFComponentBeanFactory();
        this.nodeBeanFactory = new BWFNodeBeanFactory();

        this.eventMessageBusInstance = BWFEventMessageBus.getInstance();
        this.eventMessageBusInstance.setPubEvent(EventEnum.NODE_HANDLE_SUB.getCode(), new NodeHandleSub());
        //注入系统ComponentBean eventMessageBus到BWFComponent集合中
//        bwfComponentBeanFactory.registerSingleton("BWFEventMessageBus", this.eventMessageBusInstance);
        //注入系统NodeBean eventMessageBus到BWFNode集合中
        nodeBeanFactory.registerSingleton("BWFEventMessageBus", this.eventMessageBusInstance);


    }

    @Override
    protected ConfigurableListableBeanFactory obtainBeanFactoryInitialization() {
        refreshBeanFactory();
        return getBeanFactory();
    }
}
