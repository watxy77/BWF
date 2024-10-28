package com.bwf.core.context;

import com.bwf.common.annotation.Lifecycle;
import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.factory.ConfigurableListableBeanFactory;


import java.io.Closeable;

/**
 * @Author bjweijiannan
 * @description
 */
public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {
    void setId(String id);
    @Nullable
    String getId();
    void refresh() throws IllegalStateException;//BeansException
    void registerShutdownHook();
    void close();
    void destroy();
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}
