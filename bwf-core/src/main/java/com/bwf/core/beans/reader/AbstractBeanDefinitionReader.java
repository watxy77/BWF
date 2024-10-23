package com.bwf.core.beans.reader;

import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.annotation.BWFNode;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.FileUtil;
import com.bwf.core.io.Resource;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    @Nullable
    private ClassLoader beanClassLoader;

    public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }
    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        return loadBeanDefinitions(location, null);
    }

    public int loadBeanDefinitions(String location, @Nullable Set<Resource> actualResources) throws BeanDefinitionStoreException {
        return 0;
    }

    public int loadBeanDefinitions() throws BeanDefinitionStoreException {
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
}
