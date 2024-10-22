package com.bwf.core.context;


import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.factory.BeanFactory;

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
