package com.bwf.core.beans.utils;

import com.bwf.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BeanUtil {
    private final static String _CLASS_PROTOCOL = ".class";
    private final static String _FILE_PROTOCOL = "file";
    private final static String _DOT_PROTOCOL = ".";
    private final static String _DIAGONAL_PROTOCOL = "/";

    public static String transformedBeanName(String className) {
        String beanName = "";
        if(StringUtil.isNotEmpty(className)){
            int startIndex = className.lastIndexOf(_DOT_PROTOCOL);
            beanName = className.substring(startIndex + 1, className.length());
        }
        return beanName;
    }

    public static Set<Class<?>> extractPackgeClass(String packagePath){
        ClassLoader classLoader = getClassLoader();
        return extractPackgeClass(classLoader, packagePath);
    }

    public static Set<Class<?>> extractPackgeClass(ClassLoader classLoader, String packagePath){
        String replacePath = packagePath.replace(_DOT_PROTOCOL, _DIAGONAL_PROTOCOL);
        URL url = classLoader.getResource(replacePath);
        if(url == null){
            log.error("");
            return null;
        }
        Set<Class<?>> classSet = null;
        //过滤出文件类型的资源
        if(url.getProtocol().equalsIgnoreCase(_FILE_PROTOCOL)){
            classSet = new HashSet<Class<?>>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packagePath);
        }
        //可扩展处理其他类型的资源文件
        return classSet;
    }


    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packagePath) {
        if(!fileSource.isDirectory()){
            return;
        }
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory()){
                    return true;
                }else{
                    String absolutePath = file.getAbsolutePath();
                    if(absolutePath.endsWith(_CLASS_PROTOCOL)){
                        addToClassSet(absolutePath);
                    }
                }
                return false;
            }

            private void addToClassSet(String absolutePath) {
                absolutePath = absolutePath.replace(File.separator, _DOT_PROTOCOL);
                String classPath = absolutePath.substring(absolutePath.indexOf(packagePath));
                classPath = classPath.substring(0, classPath.lastIndexOf(_DOT_PROTOCOL));
                Class<?> targetClass = loadClass(classPath);
                emptyClassSet.add(targetClass);
            }
        });
        if(files != null){
            for (File f : files) {
                //递归调用
                extractClassFile(emptyClassSet, f, packagePath);
            }
        }

    }

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    private static Class<?> loadClass(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("【"+className+"】查找className异常！");
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<?> clazz){
        return newInstance(clazz, true);
    }
    public static <T> T newInstance(Class<?> clazz, boolean accessible){
        try {
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            //设置反射对象可访问标志 true使得对象的私有属性可以被查询和设置
            declaredConstructor.setAccessible(accessible);
            return  (T)declaredConstructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }
}
