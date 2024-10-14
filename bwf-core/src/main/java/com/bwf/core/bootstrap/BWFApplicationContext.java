package com.bwf.core.bootstrap;

import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.*;
import com.bwf.common.annotation.bootstrap.BWFBeanDefinition;
import com.bwf.core.bootstrap.manager.bwfComponent.BWFComponentManager;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.io.FileUtil;
import com.bwf.core.io.ResourceLoader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bjweijiannan
 * @description
 */
public class BWFApplicationContext {
    private final static String _CLASS = ".class";
    private final static String _COM = "com";
    private static Class<?> mainApplicationClass;
    private BWFComponentManager bwfComponentManager;
    private BWFEventMessageBus eventMessageBusInstance;
    public BWFApplicationContext(Class<?>... primarySources) {
        this((ResourceLoader)null, primarySources);
    }
    public BWFApplicationContext(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.mainApplicationClass = this.deduceMainApplicationClass();
        this.bwfComponentManager = (BWFComponentManager) new BWFComponentManager().getInstance();
        this.eventMessageBusInstance = BWFEventMessageBus.getInstance();
        //注入系统ComponentBean eventMessageBus到BWFComponent集合中
        this.bwfComponentManager.injectBWFComponentBean("eventMessageBus", this.eventMessageBusInstance);

    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
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
                //扫描整个工程类
                ClassLoader mainClassLoader = mainApplicationClass.getClassLoader();
                //获取扫描根目录,处理注解类
                URL resource = mainClassLoader.getResource("");
                File file = new File(resource.getFile());
                List<String> flieList = new ArrayList<>();
                FileUtil.traverseFiles(file, flieList);
                for (String f : flieList) {
                    if (f.endsWith(_CLASS)) {
                        String className = f.substring(f.indexOf(_COM), f.indexOf(_CLASS));
                        className = className.replace(File.separator, ".");
                        Class<?> clazz = mainClassLoader.loadClass(className);
                        if(clazz.isAnnotationPresent(BWFComponent.class)){
                            //解析注解-BWFComponent
                            bwfComponentManager.createBWFBeanDefinition(className, clazz);
                        }else if(clazz.isAnnotationPresent(BWFNode.class)){
                            //解析注解-BWFNode
                        }
                    }
                }
                bwfComponentManager.scanSingleton();
                bwfComponentManager.scanSingletonMap();
                //解析注解-BWFGlobalConfigScan
                System.out.println(11111);
            }else{
               throw new NullPointerException("未发现BWFApplication注解类，无法初始化BWF框架。请在mian函数类上添加！");
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

    private Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = (new RuntimeException()).getStackTrace();
            StackTraceElement[] var2 = stackTrace;
            int var3 = stackTrace.length;
            for(int var4 = 0; var4 < var3; ++var4) {
                StackTraceElement stackTraceElement = var2[var4];
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        } catch (ClassNotFoundException var6) {
        }

        return null;
    }
}
