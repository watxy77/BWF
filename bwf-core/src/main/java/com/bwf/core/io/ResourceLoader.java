package com.bwf.core.io;
/**
 * @Author bjweijiannan
 * @description
 */
public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String var1);

//    @Nullable
    ClassLoader getClassLoader();
}
