package com.bwf.core.beans.reader.resolver;

import com.alibaba.fastjson.JSONObject;
import com.bwf.common.annotation.bootstrap.annotation.BWFComponent;
import com.bwf.common.annotation.bootstrap.annotation.BWFNode;
import com.bwf.common.annotation.bootstrap.annotation.BWFScope;
import com.bwf.core.beans.BeanNodeEnum;
import com.bwf.core.beans.reader.AbstractBeanDefinitionReader;
import com.bwf.core.beans.reader.BeanDefinitionReaderEntity;
import com.bwf.core.beans.reader.BeanReaderEnum;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.beans.utils.BeanUtil;
import com.bwf.core.context.BWFNodeBeanContext;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.FileUtil;
import com.bwf.core.io.Resource;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AnnotationBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {
    private final static String _CLASS = ".class";
    private final static String _COM = "com";

    public AnnotationBeanDefinitionReaderResolver(BWFNodeBeanContext bwfNodeBeanContext) {
        super(bwfNodeBeanContext);
    }

    public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
        int rCount = 0;
        ClassLoader classLoader = encodedResource.getTargetClass().getClassLoader();
        File file = encodedResource.getFile(encodedResource.getURL(classLoader, ""));
        List<String> flieList = new ArrayList<>();
        FileUtil.traverseFiles(file, flieList);
        List<BeanDefinitionReaderEntity> bdreList = new ArrayList<>();
        for (String f : flieList) {
            if (f.endsWith(_CLASS)) {
                String classPath = f.substring(f.indexOf(_COM), f.indexOf(_CLASS));
                try{
                    String t_classPath = classPath.replace(File.separator, ".");
                    String className = BeanUtil.transformedBeanName(t_classPath);
                    Class<?> clazz = classLoader.loadClass(t_classPath);

                    if(clazz.isAnnotationPresent(BWFNode.class)){

                        BeanDefinitionReaderEntity bdre = new BeanDefinitionReaderEntity();
                        bdre.setNode_id(className);
                        bdre.setNode_name(className);
                        bdre.setClass_name(className);
                        bdre.setClass_path(classPath);
                        bdre.setT_class_path(t_classPath);
                        bdre.setNode_type(BeanNodeEnum.NODE_JAVA.getCode());
                        bdre.setNode_class(className);
                        // 是否单例Bean
                        if(clazz.isAnnotationPresent(BWFScope.class)){
                            bdre.setSingleton(false);
                        }else{
                            bdre.setSingleton(true);
                        }
                        bdreList.add(bdre);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        rCount += this.loadBeanDefinitions(bdreList);
        return rCount;
    }
}
