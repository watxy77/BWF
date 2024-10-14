package com.bwf.core.bootstrap.manager.bwfComponent;

import com.bwf.common.annotation.bootstrap.BWFBeanDefinition;
import com.bwf.common.annotation.bootstrap.BWFInitializingBean;
import com.bwf.common.annotation.bootstrap.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.annotation.BWFScope;
import com.bwf.common.manager.BWFAnnotationManager;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.python.bouncycastle.asn1.x500.style.RFC4519Style.o;

public class BWFComponentManager implements BWFAnnotationManager {

    private static BWFComponentManager instance;
    private ConcurrentHashMap<String,String > lostBeanMaps = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> singletonBWFComponentMaps = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BWFComponentBeanDefinition> beanDefinitionBWFComponentMaps = new ConcurrentHashMap<>();

    @Override
    public Object getInstance() {
        if(instance == null){
            instance = new BWFComponentManager();
        }
        return instance;
    }

    @Override
    public Object createBean(BWFBeanDefinition BWFBeanDefinition) {
        try{
            Class clazz = BWFBeanDefinition.getClazz();
            Object instance = clazz.getConstructor().newInstance();
            //依赖注入
            for(Field field :clazz.getDeclaredFields()){
                if(field.isAnnotationPresent(BWFAutowired.class)){
                    Object bean = getBean(field.getName());
                    if(bean == null){
                        //找不到bean，无法给属性注入值
                        lostBeanMaps.put(BWFBeanDefinition.getClassName(), field.getName());
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
        }catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object getBean(String beanName) {
        Object rObject = null;
        try{
            if(beanDefinitionBWFComponentMaps.containsKey(beanName)){
                BWFComponentBeanDefinition bwfComponentBeanDefinition = beanDefinitionBWFComponentMaps.get(beanName);
                if(bwfComponentBeanDefinition.getScope().equals(SINGLETON)){
                    rObject = singletonBWFComponentMaps.get(beanName);
                }else{
                    //创建Bean对象
                    rObject = createBean(bwfComponentBeanDefinition);
                }
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
        BWFBeanDefinition.setClassName(beanName);
        BWFBeanDefinition.setClazz(clazz);
        if(clazz.isAnnotationPresent(BWFScope.class)){
            BWFScope declaredScope = (BWFScope) clazz.getDeclaredAnnotation(BWFScope.class);
            BWFBeanDefinition.setScope(PROTOTYPE);
        }else{
            BWFBeanDefinition.setScope(SINGLETON);
        }
        beanDefinitionBWFComponentMaps.put(beanName, BWFBeanDefinition);
    }

    @Override
    public void scanSingleton() {
        for(Map.Entry<String, BWFComponentBeanDefinition> entry: beanDefinitionBWFComponentMaps.entrySet()){
            String beanName = entry.getKey();
            BWFBeanDefinition BWFBeanDefinition = entry.getValue();
            if(BWFBeanDefinition.getScope().equals(SINGLETON)){
                Object bean = createBean(BWFBeanDefinition);
                singletonBWFComponentMaps.put(beanName, bean);
            }
        }
        for(Map.Entry<String, String> lostBean: lostBeanMaps.entrySet()){
            String parasitiferBeanName = lostBean.getKey();
            String autowiredBeanName = lostBean.getValue();
            Object o1 = singletonBWFComponentMaps.get(autowiredBeanName);
            Object o2 = singletonBWFComponentMaps.get(parasitiferBeanName);

            if(o1 != null && o2 != null){
                //依赖注入
                for(Field field :o2.getClass().getDeclaredFields()){
                    if(field.getName() == autowiredBeanName && field.isAnnotationPresent(BWFAutowired.class)){
                        try {
                            field.setAccessible(true);
                            field.set(o2, o1);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
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

}
