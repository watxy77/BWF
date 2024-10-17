package com.bwf.core.bootstrap;


import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.eventbus.BWFEventMessageBus;
import com.bwf.core.support.BeanFactory;

/**
 * @Author bjweijiannan
 * @description
 */
public interface ApplicationContext extends BeanFactory {
    @Nullable
    String getId();

    String getApplicationName();

    String getDisplayName();

    long getStartupDate();

    @Nullable
    ApplicationContext getParent();


}
