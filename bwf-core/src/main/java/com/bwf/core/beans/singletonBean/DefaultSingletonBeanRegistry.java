package com.bwf.core.beans.singletonBean;

import cn.hutool.core.lang.Assert;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.ObjectFactory;
import com.bwf.core.beans.factory.ConfigurableBeanFactory;
import com.bwf.core.exception.BeanCreationException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    /** 一级缓存 */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    /** 二级缓存 */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
    /** 三级缓存
     * 用于报错BeanName和创建的bean的工厂间的关系*/
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);
    private final Set<String> singletonsCurrentlyInCreation =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));
    private final Set<String> inCreationCheckExclusions =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull(singletonObject, "Singleton object must not be null");
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
//            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    @Nullable
    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        // 从单例对象缓存中获取beanName（一级缓存）
        Object singletonObject = this.singletonObjects.get(beanName);
        // 如果一级缓存中没有，并改beanName对应的是单例bean正在创建
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            //从二级缓存中获取单例对象（earlySingletonObjects里的对象是通过ObjectFactory创建出来的，还未进行属性填充）
            singletonObject = this.earlySingletonObjects.get(beanName);
            // 如果早起单例对象缓存中没有，并且允许创建早起单例对象引用
            if (singletonObject == null && allowEarlyReference) {
                // 如果为空，宋定全局变量并进行处理
                synchronized (this.singletonObjects) {
                    // Consistent creation of early reference within full singleton lock
                    //一级缓存查找（双重检查）
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        //二级缓存查找
                        singletonObject = this.earlySingletonObjects.get(beanName);
                        if (singletonObject == null) {
                            //本地三级缓存查找
                            ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                            if (singletonFactory != null) {
                                //执行lamda表达式，生成bean对象
                                singletonObject = singletonFactory.getObject();
                                //获取到bean对象后放入二级缓存
                                this.earlySingletonObjects.put(beanName, singletonObject);
                                //清除本地一级缓存的lamda表达式
                                this.singletonFactories.remove(beanName);
                            }
                        }
                    }
                }
            }
        }
        return singletonObject;
    }
    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(beanName, "Bean name must not be null");
        synchronized (this.singletonObjects) {
            Object singletonObject = this.singletonObjects.get(beanName);
            beforeSingletonCreation(beanName);
            boolean newSingleton = false;
            if (singletonObject == null) {
                try {
                    singletonObject = singletonFactory.getObject();
                    newSingleton = true;
                } catch (IllegalStateException ex) {
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        throw ex;
                    }
                }finally {
                    afterSingletonCreation(beanName);
                }
                if (newSingleton) {
                    addSingleton(beanName, singletonObject);
                }
            }
            return singletonObject;
        }
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return false;
    }

    @Override
    public String[] getSingletonNames() {
        return new String[0];
    }

    @Override
    public int getSingletonCount() {
        synchronized (this.singletonObjects) {
            return this.registeredSingletons.size();
        }
    }

    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(singletonFactory, "Singleton factory must not be null");
        // 使用singletonObjects进行加载，保障线程安全
        synchronized (this.singletonObjects) {
            // 如果一级缓存中没有beanName（bean名称-bean实例）
            if (!this.singletonObjects.containsKey(beanName)) {
                // 将beanName，singletonFactory放到单例工厂的缓存中（三级缓存）
                this.singletonFactories.put(beanName, singletonFactory);
                // 从二级缓存中清除
                this.earlySingletonObjects.remove(beanName);
                // 将beanName添加已注册的单例集合中（已注册的bean对象）
                this.registeredSingletons.add(beanName);
            }
        }
    }

    protected void beforeSingletonCreation(String beanName) {
        if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.add(beanName)) {
            throw new BeanCreationException(beanName);
        }
    }

    protected void afterSingletonCreation(String beanName) {
        if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.remove(beanName)) {
            throw new IllegalStateException("Singleton '" + beanName + "' isn't currently in creation");
        }
    }

}
