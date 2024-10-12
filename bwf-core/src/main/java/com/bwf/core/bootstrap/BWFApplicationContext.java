package com.bwf.core.bootstrap;

import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.BWFApplication;
import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.annotation.BWFScope;
import com.bwf.common.annotation.bootstrap.BWFBeanDefinition;
import com.bwf.core.bootstrap.utils.StartupInfoLogger;
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
    private static Class<?> mainApplicationClass;
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BWFBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final static  String SINGLETON = "singleton";

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
                //解析注解-BWFGlobalConfigScan

                //解析注解-BWFComponent
                this.scanBWFComponent();
                for(Map.Entry<String, BWFBeanDefinition> entry: beanDefinitionMap.entrySet()){
                    String beanName = entry.getKey();

                    BWFBeanDefinition BWFBeanDefinition = entry.getValue();
                    if(BWFBeanDefinition.getScope().equals(SINGLETON)){
                        Object bean = createBean(BWFBeanDefinition);
                        singletonObjects.put(beanName, bean);
                    }
                }
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

    private Object createBean(BWFBeanDefinition BWFBeanDefinition) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class clazz = BWFBeanDefinition.getClazz();
        Object instance = clazz.getConstructor().newInstance();
        //依赖注入
        for(Field field :clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(BWFAutowired.class)){
                Object bean = getBean(field.getName());
                if(bean == null){
                    //找不到bean，无法给属性注入值

                }
                field.setAccessible(true);
                field.set(instance, bean);
            }
        }

        //调用初始化方法
        if(instance instanceof BWFInitializingBean){
            try {
                ((BWFInitializingBean) instance).afterPropertiesSet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return instance;
    }

    public Object getBean(String beanName){
        try{
            if(beanDefinitionMap.containsKey(beanName)){
                BWFBeanDefinition BWFBeanDefinition = beanDefinitionMap.get(beanName);
                if(BWFBeanDefinition.getScope().equals(SINGLETON)){
                    Object o = singletonObjects.get(beanName);
                    return o;
                }else{
                    //创建Bean对象
                    Object bean = createBean(BWFBeanDefinition);
                    return bean;
                }
            }else{
                //Bean 不存在
                return null;
            }
        }catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void scanBWFComponent() throws ClassNotFoundException {
        //Bootstrap ----->jre/lib
        //Ext------->jre/ext/lib
        //App------->classpath
        ClassLoader mainClassLoader = mainApplicationClass.getClassLoader();
        //获取扫描根目录
        URL resource = mainClassLoader.getResource("");
        File file = new File(resource.getFile());
        List<String> flieList = new ArrayList<>();
        FileUtil.traverseFiles(file, flieList);
        for (String f : flieList) {
            if(f.endsWith(".class")){
                String className = f.substring(f.indexOf("com"), f.indexOf(".class"));
                className = className.replace(File.separator, ".");
                Class<?> clazz = mainClassLoader.loadClass(className);
                if(clazz.isAnnotationPresent(BWFComponent.class)){
                    System.out.println("----->"+className);
                    //解析，判断当前bean是单例bean，还是prototype的bean
                    //BeanDefinition
                    BWFComponent declaredAnnotations = clazz.getDeclaredAnnotation(BWFComponent.class);
                    String beanName = declaredAnnotations.value();
                    //如果beanName没有设置
                    if(StringUtils.isEmpty(beanName)){
                        // 计算从哪个位置开始截取
                        int startIndex = className.lastIndexOf(".");
                        beanName = className.substring(startIndex+1, className.length());
                    }
                    BWFBeanDefinition BWFBeanDefinition = new BWFBeanDefinition();
                    BWFBeanDefinition.setClazz(clazz);
                    if(clazz.isAnnotationPresent(BWFScope.class)){
                        BWFScope declaredScope = clazz.getDeclaredAnnotation(BWFScope.class);
                        BWFBeanDefinition.setScope(declaredScope.value());
                    }else{
                        BWFBeanDefinition.setScope(SINGLETON);
                    }
                    beanDefinitionMap.put(beanName, BWFBeanDefinition);
                }
            }
        }
    }
}
