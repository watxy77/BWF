package com.bwf.core.context;

import com.bwf.common.annotation.bootstrap.annotation.*;
import com.bwf.core.beans.factory.ConfigurableBeanFactory;
import com.bwf.core.beans.reader.BeanReaderEnum;
import com.bwf.core.beans.reader.resolver.AnnotationBeanDefinitionReaderResolver;
import com.bwf.core.beans.reader.resolver.JSONBeanDefinitionReaderResolver;
import com.bwf.core.beans.reader.resolver.XMLBeanDefinitionReaderResolver;
import com.bwf.core.beans.reader.resolver.YAMLBeanDefinitionReaderResolver;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.eventbus.model.EventEnum;
import com.bwf.core.eventbus.subscription.NodeHandleSub;
import com.bwf.core.exception.BeansException;
import com.bwf.core.io.FileUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
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
    private Object startupShutdownMonitor = new Object();
    @Nullable
    private Thread shutdownHook;

    public AbstractApplicationContext() {
        this.startupShutdownMonitor = new Object();
    }

    public BWFNodeBeanContext getBWFNodeBeanContext(){
        return bwfNodeBeanContext;
    }
    public BWFComponentBeanContext getBWFComponentBeanContext(){
        return bwfComponentBeanContext;
    }
    public static void pubEvent(String code, Object o){
        eventMessageBusInstance.setPubEvent(code, o);
    }
    public static void subEvent(String code, Object o){
        eventMessageBusInstance.setPubEvent(code, o);
    }


    @Override
    public boolean isRunning() {
        return false;
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
            /**初始化BeanFactory*/
            obtainBeanFactoryInitialization();


            this.onRefresh();
            System.out.println(StartupInfoLogger.lodeBeanMessage());
        }
    }

    private ConfigurableBeanFactory obtainBeanFactoryInitialization() {
        /**处理外部Bean生成*/
        /**1、处理XML Bean*/
        int xmlBeanCount = 0;
        boolean xmlBeanflag = false;
        if(mainApplicationClass.isAnnotationPresent(BWFConfigBeanXML.class)){
            xmlBeanflag = true;
            BWFConfigBeanXML xmlAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFConfigBeanXML.class);
            String[] xmlBeanPathArr = xmlAnnotation.value();
            xmlBeanCount = new XMLBeanDefinitionReaderResolver(bwfNodeBeanContext).loadBeanDefinitions(new EncodedResource(xmlBeanPathArr, BeanReaderEnum.BEAN_XML.getCode(), mainApplicationClass));
        }
        if(!xmlBeanflag){
            StartupInfoLogger.addLoadBeanMessage("---------【未开启】XML注解方式加载Bean对象---------");
        }else{
            StartupInfoLogger.addLoadBeanMessage("---------【开启】处理XML  Bean共加载："+ xmlBeanCount +"个Bean---------");
        }


        /**2、处理JSON Bean*/
        int jsonBeanCount = 0;
        boolean jsonBeanflag = false;
        if(mainApplicationClass.isAnnotationPresent(BWFConfigBeanJSON.class)){
            jsonBeanflag = true;
            BWFConfigBeanJSON jsonAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFConfigBeanJSON.class);
            String[] jsonBeanPathArr = jsonAnnotation.value();
            jsonBeanCount = new JSONBeanDefinitionReaderResolver(bwfNodeBeanContext).loadBeanDefinitions(new EncodedResource(jsonBeanPathArr, BeanReaderEnum.BEAN_JSON.getCode(), mainApplicationClass));
        }
        if(!jsonBeanflag){
            StartupInfoLogger.addLoadBeanMessage("---------【未开启】JSON注解方式加载Bean对象---------");
        }else{
            StartupInfoLogger.addLoadBeanMessage("---------【开启】处理JSON Bean共加载："+ jsonBeanCount +"个Bean---------");
        }


        int yamlBeanCount = 0;
        boolean yamlBeanflag = false;
        /**3、处理YAML Bean*/
        if(mainApplicationClass.isAnnotationPresent(BWFConfigBeanYAML.class)){
            yamlBeanflag = true;
            BWFConfigBeanYAML yamlAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFConfigBeanYAML.class);
            String[] yamlBeanPathArr = yamlAnnotation.value();
            yamlBeanCount = new YAMLBeanDefinitionReaderResolver(bwfNodeBeanContext).loadBeanDefinitions(new EncodedResource(yamlBeanPathArr, BeanReaderEnum.BEAN_YAML.getCode(), mainApplicationClass));
        }
        if(!yamlBeanflag){
            StartupInfoLogger.addLoadBeanMessage("---------【未开启】YAML注解方式加载Bean对象---------");
        }else{
            StartupInfoLogger.addLoadBeanMessage("---------【开启】处理YAML Bean共加载："+ yamlBeanCount +"个Bean---------");
        }

        /**4、处理Annotation Bean*/
        int annotationBeanCount = 0;
        annotationBeanCount = new AnnotationBeanDefinitionReaderResolver(bwfNodeBeanContext).loadBeanDefinitions(new EncodedResource(null, BeanReaderEnum.BEAN_ANNOTATION.getCode(), mainApplicationClass));
        if(annotationBeanCount == 0){
            StartupInfoLogger.addLoadBeanMessage("---------【未开启】Annotation注解方式加载Bean对象---------");
        }else{
            StartupInfoLogger.addLoadBeanMessage("---------【开启】处理Annotation Bean共加载："+ annotationBeanCount +"个Bean---------");
        }


        /**处理框架内部Bean生成*/
        /**1、处理Annotation Bean*/

        //实例化BWFComponent单实例（非懒加载的）bean的声明周期（进行bean对象的创建工作）
//        finishBWFComponentBeanFactoryInitialization();
        return null;
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

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }

    protected void finishBWFComponentBeanFactoryInitialization() {
        try{
            StartupInfoLogger.addLoadBeanMessage("---------BWFComponentBean生成---------");
            StartupInfoLogger.addLoadBeanMessage("扫描出具有BWFComponent注解的类数量：" + bwfComponentClazzMap.size());
            for (Map.Entry<String, Class<?>> entry : bwfComponentClazzMap.entrySet()) {
                String className = entry.getKey();
                Class<?> clazz = entry.getValue();
                StartupInfoLogger.addLoadBeanMessage("className：" + className);
                //解析注解-BWFComponent
                bwfComponentBeanContext.preInstantiateSingletons(className, clazz);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void finishBWFNodeBeanFactoryInitialization() {
        try{
            StartupInfoLogger.addLoadBeanMessage("---------BWFNodeBean生成---------");
            StartupInfoLogger.addLoadBeanMessage("扫描出具有BWFNode注解的类数量：" + bwfNodeClazzMap.size());
            for (Map.Entry<String, Class<?>> entry : bwfNodeClazzMap.entrySet()) {
                String className = entry.getKey();
                Class<?> clazz = entry.getValue();
                StartupInfoLogger.addLoadBeanMessage("className：" + className);
                //解析注解-BWFNode
                bwfNodeBeanContext.preInstantiateSingletons(className, clazz);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void prepareRefresh() {
        this.startupDate = System.currentTimeMillis();
        this.closed.set(false);
        this.active.set(true);
        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
        this.eventMessageBusInstance = BWFEventMessageBus.getInstance();
        this.eventMessageBusInstance.setPubEvent(EventEnum.NODE_HANDLE_SUB.getCode(), new NodeHandleSub());
        this.bwfComponentBeanContext = new BWFComponentBeanContext();
        this.bwfNodeBeanContext = new BWFNodeBeanContext();
        //注入系统ComponentBean eventMessageBus到BWFComponent集合中
        bwfComponentBeanContext.registerSingleton("BWFEventMessageBus", this.eventMessageBusInstance);


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

    protected void onRefresh() throws BeansException {
    }

}
