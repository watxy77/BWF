package com.bwf.core.beans;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**无视反射和序列化的单例攻击*/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BWFNodeBeanFactory extends AbstractBeanFactory {
    public static BWFNodeBeanFactory getInstance(){
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder{
        HOLDER;
        private BWFNodeBeanFactory instance;
        ContainerHolder(){
            instance = new BWFNodeBeanFactory();
        }
    }

}
