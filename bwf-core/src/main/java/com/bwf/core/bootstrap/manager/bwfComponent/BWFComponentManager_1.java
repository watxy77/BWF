package com.bwf.core.bootstrap.manager.bwfComponent;

import com.bwf.common.annotation.bootstrap.BWFBeanDefinition;
import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.manager.BWFAnnotationManager;
import com.bwf.common.utils.StringUtils;


import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BWFComponentManager_1 implements BWFAnnotationManager {
    private final static String _CONSUMER = "consumer";
    private static BWFComponentManager_1 BWFCMInstance;
    private ConcurrentHashMap<String,String > autowiredBeanMaps = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> singletonBWFComponentMaps = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BWFComponentBeanDefinition> beanDefinitionBWFComponentMaps = new ConcurrentHashMap<>();

    @Override
    public Object getInstance() {
        if(BWFCMInstance == null){
            BWFCMInstance = new BWFComponentManager_1();
        }
        return BWFCMInstance;
    }

    @Override
    public Object createBean(BWFBeanDefinition BWFBeanDefinition) {
        try{
            Class clazz = BWFBeanDefinition.getClazz();
            Object instance = clazz.getConstructor().newInstance();
            //依赖注入
            for(Field field :clazz.getDeclaredFields()){
                if(field.isAnnotationPresent(BWFAutowired.class)){
                    autowiredBeanMaps.put(BWFBeanDefinition.getClassName(), field.getName());
//                    Object bean = getBean(field.getName());
//                    if(bean == null){
//                        //找不到bean，无法给属性注入值
//                        lostBeanMaps.put(BWFBeanDefinition.getClassName(), field.getName());
//                    }
//                    field.setAccessible(true);
//                    field.set(instance, bean);
                }
                return null;
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
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object getBean(String beanName) {
        Object rObject = null;
        try{
            if(beanDefinitionBWFComponentMaps.containsKey(beanName)){
                BWFComponentBeanDefinition bwfComponentBeanDefinition = beanDefinitionBWFComponentMaps.get(beanName);
//                if(bwfComponentBeanDefinition.getScope().equals(SINGLETON)){
//                    rObject = singletonBWFComponentMaps.get(beanName);
//                }else{
//                    //创建Bean对象
//                    rObject = createBean(bwfComponentBeanDefinition);
//                }
            }else{
                //Bean 不存在对应的bean
                throw new NullPointerException();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return rObject;
    }

    @Override
    public void createBWFBeanDefinition(String className, Class clazz) {
        //解析，判断当前bean是单例bean，还是prototype的bean
        //BeanDefinition
        BWFComponent declaredAnnotations = (BWFComponent) clazz.getDeclaredAnnotation(BWFComponent.class);
        String beanName = declaredAnnotations.value();
        //如果beanName没有设置,获取名称
        if(StringUtils.isEmpty(beanName)){
            int startIndex = className.lastIndexOf(".");
            beanName = className.substring(startIndex + 1, className.length());
        }
        //生成BeanDefinition类
        BWFComponentBeanDefinition BWFBeanDefinition = new BWFComponentBeanDefinition();
//        BWFBeanDefinition.setClassName(beanName);
//        BWFBeanDefinition.setClazz(clazz);
//        BWFBeanDefinition.setType(_CONSUMER);
//        if(clazz.isAnnotationPresent(BWFScope.class)){
//            BWFScope declaredScope = (BWFScope) clazz.getDeclaredAnnotation(BWFScope.class);
//            BWFBeanDefinition.setScope(PROTOTYPE);
//        }else{
//            BWFBeanDefinition.setScope(SINGLETON);
//        }
        beanDefinitionBWFComponentMaps.put(beanName, BWFBeanDefinition);
    }

    @Override
    public void scanSingleton() {
        for(Map.Entry<String, BWFComponentBeanDefinition> entry: beanDefinitionBWFComponentMaps.entrySet()){
            String beanName = entry.getKey();
//            BWFBeanDefinition BWFBeanDefinition = entry.getValue();
//            if(BWFBeanDefinition.getScope().equals(SINGLETON)){
//                Object bean = createBean(BWFBeanDefinition);
//                singletonBWFComponentMaps.put(beanName, bean);
//            }
        }

//        //依赖注入
//        for(Map.Entry<String, Object> lostBean: singletonBWFComponentMaps.entrySet()){
//            String className = lostBean.getKey();
//            Object value = lostBean.getValue();
//            if(value != null){
//                try {
//                    Class clazz = value.getClass();
//                    Object instanceO = clazz.getConstructor().newInstance();
//                    //依赖注入
//                    for(Field field :clazz.getDeclaredFields()){
//                        if(field.isAnnotationPresent(BWFAutowired.class)){
//                            try {
//                                System.out.println("variable name:"+field.getName());
//                                Object bean = getBean(field.getName());
//                                System.out.println(bean);
//                                field.setAccessible(true);
//                                field.set(instanceO, bean);
//                                System.out.println(field);
//
//                            } catch (IllegalAccessException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    }
//                    //调用初始化方法
//                    if(instanceO instanceof BWFInitializingBean){
//                        try {
//                            ((BWFInitializingBean) instanceO).afterPropertiesSet();
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                } catch (InstantiationException e) {
//                    throw new RuntimeException(e);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                } catch (NoSuchMethodException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }

    public void scanSingletonMap() {
        for(Map.Entry<String, Object> lostBean: singletonBWFComponentMaps.entrySet()){
            String beanName = lostBean.getKey();
            Object value = lostBean.getValue();
            if(value instanceof BWFInitializingBean){
                try {
                    ((BWFInitializingBean) value).afterPropertiesSet();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void injectBWFComponentBean(String className, Object object){
        BWFComponentBeanDefinition bwfBeanDefinition = new BWFComponentBeanDefinition();
//        bwfBeanDefinition.setClassName(className);
//        bwfBeanDefinition.setClazz(object.getClass());
//        bwfBeanDefinition.setScope(SINGLETON);
//        bwfBeanDefinition.setType("system");
        beanDefinitionBWFComponentMaps.put(className, bwfBeanDefinition);
        singletonBWFComponentMaps.put(className, object);
    }

}
