package com.bwf.core.context;


import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.factory.BeanFactory;
import com.bwf.core.beans.factory.ListableBeanFactory;

/**
 * @Author bjweijiannan
 * @description
 */
public interface ApplicationContext extends ListableBeanFactory {

    String getDisplayName();

    long getStartupDate();

    @Nullable
    ApplicationContext getParent();


}
