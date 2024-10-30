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
import com.bwf.core.context.AbstractApplicationContext;
import com.bwf.core.exception.BeanDefinitionStoreException;
import com.bwf.core.io.FileUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotationBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Arrays.asList(BWFNode.class, BWFScope.class);

    public AnnotationBeanDefinitionReaderResolver(ConfigurableListableBeanFactory beanFactory, String[] beanPathArr) {
        super(beanFactory, beanPathArr);
    }

    @Override
    public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
        int rCount = 0;
        // 获取TargetClass所在目录下的class类
        Set<Class<?>> classSet = BeanUtil.extractPackgeClass(encodedResource.getTargetClass().getClassLoader(), encodedResource.getTargetClass().getName().substring(0, encodedResource.getTargetClass().getName().lastIndexOf(".")));
        List<BeanDefinitionDocument> bddList = new ArrayList<>();
        // 扫描出需要转换为BWFNode的class
        for (Class<?> clazz : classSet) {
            try{
                if(clazz.isAnnotationPresent(BWFNode.class)){
                    String className = clazz.getName();
                    String beanName = BeanUtil.transformedBeanName(className);
                    BeanDefinitionDocument bdd = new BeanDefinitionDocument();
                    bdd.setBeanId(beanName);
                    bdd.setBeanName(beanName);
                    bdd.setBeanType(BeanNodeEnum.NODE_JAVA.getCode());
                    bdd.setClassName(className);
                    bdd.setClassPath(className);
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
        // 生成BeanDefinitions
        rCount += this.loadBeanDefinitions(bddList, BeanReaderEnum.BEAN_ANNOTATION.getCode());
        return rCount;
    }
}
