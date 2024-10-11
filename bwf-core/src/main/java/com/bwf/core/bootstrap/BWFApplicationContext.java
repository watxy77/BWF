package com.bwf.core.bootstrap;

import com.bwf.common.annotation.bootstrap.BWFApplication;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.io.ResourceLoader;

import java.lang.annotation.Annotation;
import java.time.Duration;

/**
 * @Author bjweijiannan
 * @description
 */
public class BWFApplicationContext {
    protected static Class<?> mainApplicationClass;
    public BWFApplicationContext(Class<?>... primarySources) {
        this((ResourceLoader)null, primarySources);
    }
    public BWFApplicationContext(ResourceLoader resourceLoader, Class<?>... primarySources) {

    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        mainApplicationClass = primarySource;
        return run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {

        return (new BWFApplicationContext(primarySources)).run(args);
    }
    public ConfigurableApplicationContext run(String... args) {
        long startTime = System.nanoTime();
        ConfigurableApplicationContext context = null;
        try{
            //解析注解-BWFApplication
            BWFApplication declaredAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFApplication.class);
            if(declaredAnnotation != null){
                String[] value = declaredAnnotation.value();
                //解析注解-BWFGlobalConfigScan
                //解析注解-BWFComponent
                //解析注解-BWFNode
            }else{

            }





        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //启动所需时间
            Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
            (new StartupInfoLogger(this.mainApplicationClass)).logStarted(timeTakenToStartup);
        }
        return context;
    }

    public Object getBean(String beanName){
        return null;
    }
}
