package com.bwf.core.context;

import com.bwf.common.annotation.bootstrap.annotation.*;

import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.beans.reader.BeanDefinitionReader;
import com.bwf.core.beans.reader.BeanReaderEnum;
import com.bwf.core.beans.reader.resolver.AnnotationBeanDefinitionReaderResolver;
import com.bwf.core.beans.reader.resolver.JSONBeanDefinitionReaderResolver;
import com.bwf.core.beans.reader.resolver.XMLBeanDefinitionReaderResolver;
import com.bwf.core.beans.reader.resolver.YAMLBeanDefinitionReaderResolver;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.beans.utils.BeanUtil;
import com.bwf.core.bootstrap.utils.ClassUtils;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
import com.bwf.core.exception.BeansException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import sun.reflect.annotation.*;

public abstract class AbstractApplicationContext implements ConfigurableApplicationContext{
    private String id = ClassUtils.identityToString(this);
    /**系统启动的开始时间*/
    protected long startupDate;
    /**上下文是否已关闭的标志*/
    private final AtomicBoolean closed = new AtomicBoolean();
    /**上下文是否活动的标志*/
    private final AtomicBoolean active = new AtomicBoolean();
    private final Map<String, Class<?>> bwfComponentClazzMap = new HashMap<>();
    private final Map<String, Class<?>> bwfNodeClazzMap = new HashMap<>();
    private final List<BeanDefinitionReader> beanDefinitionReaders = new ArrayList<>();
    protected static Class<?> mainApplicationClass;
    protected Set<Class<?>> primarySources;

    /**“刷新”和“销毁”的监视器*/
    private Object startupShutdownMonitor = new Object();
    @Nullable
    private Thread shutdownHook;

    public AbstractApplicationContext() {
        this.startupShutdownMonitor = new Object();
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
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
    public void refresh() throws IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            /**
             * 容器属性前的准备工作
             * 1、设置容器的启动时间
             * 2、设置活跃状态为true
             * 3、设置关闭状态为false
             * */
            prepareRefresh();
            /**初始化项目中的Bean Factory*/
            ConfigurableListableBeanFactory beanFactory = obtainBeanFactoryInitialization();
            try{
                // 注册Bean的扫描器
                registerBeanDefinitionReaderPostProcessors(beanFactory);
                // 子类覆盖方法做额外的处理
                postProcessBeanFactory(beanFactory);
                // 调用各种beanFactory处理器
                invokeBeanFactoryPostProcessors(beanFactory);
                // 注册bean处理器，这里是注册功能，调用的是getBean方法
                registerBeanPostProcessors(beanFactory);
                // 留给子类来处理其他的bean
                onRefresh();
                //实例化剩下的单实例（非懒加载的）bean的声明周期（进行bean对象的创建工作）
                finishBeanFactoryInitialization(beanFactory);
            }catch (Exception e){
                e.printStackTrace();
            }

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
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void registerBeanDefinitionReader(BeanDefinitionReader bdr) {
        this.beanDefinitionReaders.add(bdr);
    }

    protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;
    protected abstract ConfigurableListableBeanFactory obtainBeanFactoryInitialization();

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        try{
//            StartupInfoLogger.addLoadBeanMessage("---------BWFComponentBean生成---------");
//            StartupInfoLogger.addLoadBeanMessage("扫描出具有BWFComponent注解的类数量：" + bwfComponentClazzMap.size());
//            for (Map.Entry<String, Class<?>> entry : bwfComponentClazzMap.entrySet()) {
//                String className = entry.getKey();
//                Class<?> clazz = entry.getValue();
//                StartupInfoLogger.addLoadBeanMessage("className：" + className);
//                //解析注解-BWFComponent
////                bwfComponentBeanContext.preInstantiateSingletons(className, clazz);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void prepareRefresh() {
        this.startupDate = System.currentTimeMillis();
        this.closed.set(false);
        this.active.set(true);
        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
    }



    protected void onRefresh() throws BeansException {
    }

    protected void registerBeanDefinitionReaderPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        /**注册Bean扫描器*/
        /**1、处理XML Bean扫描器*/
        if(mainApplicationClass.isAnnotationPresent(BWFConfigBeanXML.class)){
            BWFConfigBeanXML xmlAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFConfigBeanXML.class);
            String[] xmlBeanPathArr = xmlAnnotation.value();
            registerBeanDefinitionReader(new XMLBeanDefinitionReaderResolver(beanFactory, xmlBeanPathArr));
        }

        /**2、处理JSON Bean扫描器*/
        if(mainApplicationClass.isAnnotationPresent(BWFConfigBeanJSON.class)){
            BWFConfigBeanJSON jsonAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFConfigBeanJSON.class);
            String[] jsonBeanPathArr = jsonAnnotation.value();
            registerBeanDefinitionReader(new JSONBeanDefinitionReaderResolver(beanFactory, jsonBeanPathArr));
        }

        /**3、处理YAML Bean扫描器*/
        if(mainApplicationClass.isAnnotationPresent(BWFConfigBeanYAML.class)){
            BWFConfigBeanYAML yamlAnnotation = mainApplicationClass.getDeclaredAnnotation(BWFConfigBeanYAML.class);
            String[] yamlBeanPathArr = yamlAnnotation.value();
            registerBeanDefinitionReader(new YAMLBeanDefinitionReaderResolver(beanFactory, yamlBeanPathArr));
        }

        /**4、处理Annotation Bean扫描器*/
        registerBeanDefinitionReader(new AnnotationBeanDefinitionReaderResolver(beanFactory,null));

        /**处理框架内部Bean生成*/
        /**1、处理Annotation Bean*/
    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        for (BeanDefinitionReader beanDefinitionReader : this.beanDefinitionReaders) {
            int beanCount = 0;
            String className = beanDefinitionReader.getClass().getName();
            className = BeanUtil.transformedBeanName(className);
            String[] beanPathArr = beanDefinitionReader.getBeanPathArr();
            beanCount = beanDefinitionReader.loadBeanDefinitions(new EncodedResource(beanPathArr, className, mainApplicationClass));
            StartupInfoLogger.addLoadBeanMessage("---------【处理】"+ className +"类共加载："+ beanCount +"个Bean对象---------");
        }
    }
}
