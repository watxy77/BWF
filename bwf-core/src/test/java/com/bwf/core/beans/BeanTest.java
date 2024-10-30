package com.bwf.core.beans;

import com.bwf.core.beans.proxy.groovy.GroovyNodeProxy;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class BeanTest {
    @Test
    public void createChainTest(){
        PropertyValue pv1 = new PropertyValue("a1", "System.out.println(record);", 1);
        PropertyValue pv2 = new PropertyValue("a2", "bbb", 76);
        PropertyValue pv3 = new PropertyValue("a3", "ccc", 26);
        PropertyValue pv4 = new PropertyValue("a4", "ddd", 65);

        GroovyNodeProxy groovyNodeProxy = new GroovyNodeProxy(pv1);
        GroovyNodeProxy jsHandlerProxy = new GroovyNodeProxy(pv2);
        GroovyNodeProxy luaProxyHandler = new GroovyNodeProxy(pv3);
        groovyNodeProxy.setNext(jsHandlerProxy).setNext(luaProxyHandler);
        groovyNodeProxy.invoke(null);

        PropertyValue pvx = new PropertyValue("a1", "System.out.println(\"kkkk\");", 1);
        jsHandlerProxy.updatePV(pvx);
        groovyNodeProxy.invoke(null);
    }
}
