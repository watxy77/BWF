package com.bwf.core.beans.utils;

import com.bwf.core.context.AbstractApplicationContext;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.Set;

public class BeanUtilText {
    @DisplayName("提取目标类的方法：extractPackgeClass")
    @Test
    public void extractPackgeClassTest(){
        Set<Class<?>> classSet = BeanUtil.extractPackgeClass(AbstractApplicationContext.class.getClassLoader(),"com.bwf.core.context");
        System.out.println(classSet);
        Assertions.assertEquals(4, classSet.size());
    }
}
