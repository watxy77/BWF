package com.bwf.core.context;

import com.bwf.common.annotation.bootstrap.annotation.*;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.io.FileUtil;
import com.bwf.core.io.ResourceLoader;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.*;

/**
 * @Author bjweijiannan
 * @description
 */
public class BWFApplicationContext{
    private final static String _CLASS = ".class";
    private final static String _COM = "com";
    private static Class<?> mainApplicationClass;
    private Set<Class<?>> primarySources;
    private static BWFEventMessageBus eventMessageBusInstance;
    private BWFComponentBeanContext bwfComponentBeanContext;

    public BWFApplicationContext(Class<?>... primarySources) {
        this((ResourceLoader)null, primarySources);
    }
    public BWFApplicationContext(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.mainApplicationClass = this.deduceMainApplicationClass();
        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
        this.eventMessageBusInstance = BWFEventMessageBus.getInstance();
        this.bwfComponentBeanContext = new BWFComponentBeanContext();
        //注入系统ComponentBean eventMessageBus到BWFComponent集合中
        bwfComponentBeanContext.registerSingleton("eventMessageBus", this.eventMessageBusInstance);
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return (new BWFApplicationContext(primarySources)).run(args);
    }
    public ConfigurableApplicationContext run(String... args) {
        long startTime = System.nanoTime();
        ConfigurableApplicationContext c = null;
        try{
            //解析注解-BWFApplication
            BWFApplication declaredAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFApplication.class);
            if(declaredAnnotation != null){
                //扫描MainApplicationClassPath
                this.scanMainApplicationClassPath();

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
        return c;
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

    private void scanMainApplicationClassPath(){
        try{
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
                        bwfComponentBeanContext.createBean(className, clazz);
//                            bwfComponentManager.createBWFBeanDefinition(className, clazz);
                    }else if(clazz.isAnnotationPresent(BWFNode.class)){
                        //解析注解-BWFNode
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void pubEvent(String code, Object o){
        eventMessageBusInstance.setPubEvent(code, o);
    }

    public static void subEvent(String code, Object o){
        eventMessageBusInstance.setPubEvent(code, o);
    }
}
