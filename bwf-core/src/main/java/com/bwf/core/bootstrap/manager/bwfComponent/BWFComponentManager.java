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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.python.bouncycastle.asn1.x500.style.RFC4519Style.o;

public class BWFComponentManager implements BWFAnnotationManager {

    private static BWFComponentManager instance;
    private ConcurrentHashMap<String,Object> singletonBWFComponentObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BWFComponentBeanDefinition> beanDefinitionBWFComponentMap = new ConcurrentHashMap<>();

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
        BWFBeanDefinition bwfBeanDefinition = null;
        try{
            if(beanDefinitionBWFComponentMap.containsKey(beanName)){
                bwfBeanDefinition = beanDefinitionBWFComponentMap.get(beanName);
                if(bwfBeanDefinition.getScope().equals(SINGLETON)){
                    bwfBeanDefinition = (BWFBeanDefinition) singletonBWFComponentObjects.get(beanName);

                }else{
                    //创建Bean对象
                    bwfBeanDefinition = (BWFBeanDefinition) createBean(bwfBeanDefinition);
                }
            }else{
                //Bean 不存在
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bwfBeanDefinition;
    }

    @Override
    public void createSingleton(String className, Class clazz) {
        System.out.println("----->"+className);
        //解析，判断当前bean是单例bean，还是prototype的bean
        //BeanDefinition
        BWFComponent declaredAnnotations = (BWFComponent) clazz.getDeclaredAnnotation(BWFComponent.class);
        String beanName = declaredAnnotations.value();
        //如果beanName没有设置,获取名称
        if(StringUtils.isEmpty(beanName)){
            int startIndex = className.lastIndexOf(".");
            beanName = className.substring(startIndex+1, className.length());
        }
        //生成BeanDefinition类
        BWFComponentBeanDefinition BWFBeanDefinition = new BWFComponentBeanDefinition();
        BWFBeanDefinition.setClazz(clazz);
        if(clazz.isAnnotationPresent(BWFScope.class)){
            BWFScope declaredScope = (BWFScope) clazz.getDeclaredAnnotation(BWFScope.class);
            BWFBeanDefinition.setScope(declaredScope.value());
        }else{
            BWFBeanDefinition.setScope(SINGLETON);

        }
        beanDefinitionBWFComponentMap.put(beanName, BWFBeanDefinition);
    }

    @Override
    public void scanSingleton() {
        for(Map.Entry<String, BWFComponentBeanDefinition> entry: beanDefinitionBWFComponentMap.entrySet()){
            String beanName = entry.getKey();
            BWFBeanDefinition BWFBeanDefinition = entry.getValue();
            if(BWFBeanDefinition.getScope().equals(SINGLETON)){
                Object bean = createBean(BWFBeanDefinition);
                singletonBWFComponentObjects.put(beanName, bean);
            }
        }
    }
}
