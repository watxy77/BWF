package com.bwf.core.bootstrap;


import com.bwf.common.annotation.bootstrap.annotation.Nullable;

/**
 * @Author bjweijiannan
 * @description
 */
public interface ApplicationContext {
    @Nullable
    String getId();

    String getApplicationName();

    String getDisplayName();

    long getStartupDate();

    @Nullable
    ApplicationContext getParent();

}
