package com.bwf.core.beans.reader.resolver;

import com.alibaba.fastjson.JSONObject;
import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;
import com.bwf.core.beans.reader.AbstractBeanDefinitionReader;
import com.bwf.core.beans.reader.BeanDefinitionDocument;
import com.bwf.core.beans.reader.BeanReaderEnum;
import com.bwf.core.beans.resource.EncodedResource;
import com.bwf.core.beans.BWFNodeBeanFactory;
import com.bwf.core.exception.BeanDefinitionStoreException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONBeanDefinitionReaderResolver extends AbstractBeanDefinitionReader {


    public JSONBeanDefinitionReaderResolver(ConfigurableListableBeanFactory beanFactory, String[] beanPathArr) {
        super(beanFactory, beanPathArr);
    }

    @Override
    public int loadBeanDefinitions(EncodedResource resource) throws BeanDefinitionStoreException {
        String[] pathArr = resource.getPath();
        int rCount = 0;
        if(pathArr.length > 0){
            for (String path : pathArr) {
                try {
                    File file = resource.getFile(resource.getURL(resource.getTargetClass().getClassLoader(), path));
                    String content = FileUtils.readFileToString(file, this.charsetName);
                    JSONObject beanJson = JSONObject.parseObject(content);
                    String bwfNodeArr = beanJson.getString(this.bwf_node);
                    List<BeanDefinitionDocument> bdreList = JSONObject.parseArray(bwfNodeArr, BeanDefinitionDocument.class);
                    rCount += this.loadBeanDefinitions(bdreList, BeanReaderEnum.BEAN_JSON.getCode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rCount;
    }
}
