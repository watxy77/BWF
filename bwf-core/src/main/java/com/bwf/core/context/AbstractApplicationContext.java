package com.bwf.core.context;

import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.annotation.BWFNode;
import com.bwf.common.utils.StringUtils;
import com.bwf.core.beans.RootBeanDefinition;
import com.bwf.core.bootstrap.ApplicationContext;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.exception.BeansException;
import com.bwf.core.io.FileUtil;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext{
    /**系统启动的开始时间*/
    protected long startupDate;
    /**上下文是否已关闭的标志*/
    private final AtomicBoolean closed = new AtomicBoolean();
    /**上下文是否活动的标志*/
    private final AtomicBoolean active = new AtomicBoolean();
    private final Map<String, Class<?>> bwfComponentClazzMap = new HashMap<>();
    private final Map<String, Class<?>> bwfNodeClazzMap = new HashMap<>();
    private final static String _CLASS = ".class";
    private final static String _COM = "com";
    protected static Class<?> mainApplicationClass;
    protected Set<Class<?>> primarySources;
    private static BWFEventMessageBus eventMessageBusInstance;
    private BWFComponentBeanContext bwfComponentBeanContext;
    private BWFNodeBeanContext bwfNodeBeanContext;

    /**“刷新”和“销毁”的监视器*/
    private final Object startupShutdownMonitor = new Object();




    public static void pubEvent(String code, Object o){
        eventMessageBusInstance.setPubEvent(code, o);
    }

    public static void subEvent(String code, Object o){
        eventMessageBusInstance.setPubEvent(code, o);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Override
    public ApplicationContext getParent() {
        return null;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setParent(ApplicationContext parent) {

    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {

    }

    @Override
    public void refresh() throws IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            /**
             * 容器属性前的准备工作
             * 1、设置容器的启动时间
             * 2、设置活跃状态为true
             * 3、设置关闭状态为false
             * */
            prepareRefresh();
            //实例化BWFComponent单实例（非懒加载的）bean的声明周期（进行bean对象的创建工作）
            finishBWFComponentBeanFactoryInitialization();
            //实例化BWFNode单实例（非懒加载的）bean的声明周期（进行bean对象的创建工作）
            finishBWFNodeBeanFactoryInitialization();
            //解析注解-BWFGlobalConfigScan
            System.out.println(11111);
        }
    }

    @Override
    public void registerShutdownHook() {

    }

    @Override
    public void close() {
//        synchronized (this.startupShutdownMonitor) {
//            doClose();
//            // If we registered a JVM shutdown hook, we don't need it anymore now:
//            // We've already explicitly closed the context.
//            if (this.shutdownHook != null) {
//                try {
//                    Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
//                }
//                catch (IllegalStateException ex) {
//                    // ignore - VM is already shutting down
//                }
//            }
//        }
    }

    @Override
    public void destroy() {
        close();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    protected void finishBWFComponentBeanFactoryInitialization() {
        try{
            for (Map.Entry<String, Class<?>> entry : bwfComponentClazzMap.entrySet()) {
                String className = entry.getKey();
                Class<?> clazz = entry.getValue();

                //解析注解-BWFComponent
                bwfComponentBeanContext.preInstantiateSingletons(className, clazz);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void finishBWFNodeBeanFactoryInitialization() {
        try{
            for (Map.Entry<String, Class<?>> entry : bwfNodeClazzMap.entrySet()) {
                String className = entry.getKey();
                Class<?> clazz = entry.getValue();
                BWFNode declaredAnnotations = (BWFNode) clazz.getDeclaredAnnotation(BWFNode.class);
                String beanName = declaredAnnotations.value();
                //如果beanName没有设置,获取名称
                if(StringUtils.isEmpty(beanName)){
                    int startIndex = className.lastIndexOf(".");
                    beanName = className.substring(startIndex + 1, className.length());
                }
                //解析注解-BWFComponent
//                bwfNodeBeanContext.createBean(beanName, clazz);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void prepareRefresh() {
        this.startupDate = System.currentTimeMillis();
        this.closed.set(false);
        this.active.set(true);

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
                try{
                    className = className.replace(File.separator, ".");
                    Class<?> clazz = mainClassLoader.loadClass(className);
                    if(clazz.isAnnotationPresent(BWFComponent.class)){
                        bwfComponentClazzMap.put(className, clazz);
                    }else if(clazz.isAnnotationPresent(BWFNode.class)){
                        bwfNodeClazzMap.put(className, clazz);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
        this.eventMessageBusInstance = BWFEventMessageBus.getInstance();
        this.bwfComponentBeanContext = new BWFComponentBeanContext();
        this.bwfNodeBeanContext = new BWFNodeBeanContext();
        //注入系统ComponentBean eventMessageBus到BWFComponent集合中
        bwfComponentBeanContext.registerSingleton("eventMessageBus", this.eventMessageBusInstance);
    }

    protected Class<?> deduceMainApplicationClass() {
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
