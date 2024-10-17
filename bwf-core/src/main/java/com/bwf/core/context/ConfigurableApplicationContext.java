package com.bwf.core.context;


import com.bwf.common.annotation.Lifecycle;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.bootstrap.ApplicationContext;


import java.io.Closeable;

/**
 * @Author bjweijiannan
 * @description
 */
public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {
    void setId(String id);
    void setParent(@Nullable ApplicationContext parent);
    void setClassLoader(ClassLoader classLoader);
    void refresh() throws IllegalStateException;//BeansException,
    void registerShutdownHook();
    void close();
    void destroy();
    boolean isActive();
}
