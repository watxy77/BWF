package com.bwf.core.beans.reader.resolver;

import com.bwf.common.annotation.bootstrap.annotation.BWFNode;
import com.bwf.common.annotation.bootstrap.annotation.BWFScope;
import com.bwf.core.beans.BeanNodeEnum;
import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.beans.reader.AbstractBeanDefinitionReader;
import com.bwf.core.beans.reader.BeanDefinitionDocument;
import com.bwf.core.beans.reader.BeanReaderEnum;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.beans.utils.BeanUtil;
import com.bwf.core.beans.BWFNodeBeanFactory;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnnotationBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {
    private final static String _CLASS = ".class";
    private final static String _COM = "com";

    public AnnotationBeanDefinitionReaderResolver(ConfigurableListableBeanFactory beanFactory, String[] beanPathArr) {
        super(beanFactory, beanPathArr);
    }

    @Override
    public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
        int rCount = 0;
        ClassLoader classLoader = encodedResource.getTargetClass().getClassLoader();
        File file = encodedResource.getFile(encodedResource.getURL(classLoader, ""));
        List<String> flieList = new ArrayList<>();
        FileUtil.traverseFiles(file, flieList);
        List<BeanDefinitionDocument> bddList = new ArrayList<>();
        for (String f : flieList) {
            if (f.endsWith(_CLASS)) {
                String classPath = f.substring(f.indexOf(_COM), f.indexOf(_CLASS));
                try{
                    String t_classPath = classPath.replace(File.separator, ".");
                    String className = BeanUtil.transformedBeanName(t_classPath);
                    Class<?> clazz = classLoader.loadClass(t_classPath);

                    if(clazz.isAnnotationPresent(BWFNode.class)){
                        BeanDefinitionDocument bdd = new BeanDefinitionDocument();
                        bdd.setBeanId(className);
                        bdd.setBeanName(className);
                        bdd.setBeanType(BeanNodeEnum.NODE_JAVA.getCode());
                        bdd.setClassName(className);
                        bdd.setClassPath(classPath);
                        bdd.settClassPath(t_classPath);
                        // 是否单例Bean
                        if(clazz.isAnnotationPresent(BWFScope.class)){
                            bdd.setSingleton(false);
                        }else{
                            bdd.setSingleton(true);
                        }
                        bddList.add(bdd);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        rCount += this.loadBeanDefinitions(bddList, BeanReaderEnum.BEAN_ANNOTATION.getCode());
        return rCount;
    }
}
