package com.bwf.core.bootstrap;


import com.bwf.common.annotation.Lifecycle;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;


import java.io.Closeable;

/**
 * @Author bjweijiannan
 * @description
 */
public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {
    void setId(String var1);
    void setParent(@Nullable ApplicationContext var1);
    void setClassLoader(ClassLoader var1);
    void refresh() throws IllegalStateException;//BeansException,
    void registerShutdownHook();
    void close();
    boolean isActive();
}
