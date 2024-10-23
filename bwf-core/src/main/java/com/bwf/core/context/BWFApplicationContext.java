package com.bwf.core.context;

import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.*;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.exception.BeansException;
import com.bwf.core.io.ResourceLoader;

import java.time.Duration;

/**
 * @Author bjweijiannan
 * @description
 */
public class BWFApplicationContext extends AbstractApplicationContext{

    public BWFApplicationContext(Class<?>... primarySources) {
        this((ResourceLoader)null, primarySources);
    }
    public BWFApplicationContext(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.mainApplicationClass = this.deduceMainApplicationClass();
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
                refresh();
            }else{
                throw new NullPointerException("未发现BWFApplication注解类，无法初始化BWF框架。请在main启动函数类上添加！");
            }

        }catch (Exception e){
            e.printStackTrace();
            //destroyBeans();
            //cancelRefresh(ex);
            throw e;
        }finally {
            //启动所需时间
            Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - this.startupDate);
            (new StartupInfoLogger(this.mainApplicationClass)).logStarted(timeTakenToStartup);
        }

        return this;
    }

    @Override
    public Object getComponentBean(String beanName) {
        return this.getBWFComponentBeanContext().getBean(beanName);
    }

    @Override
    public Object getNodeBean(String beanName) {
        return this.getBWFNodeBeanContext().getBean(beanName);
    }
}
