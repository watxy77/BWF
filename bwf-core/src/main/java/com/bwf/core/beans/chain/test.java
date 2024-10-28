package com.bwf.core.beans.chain;

import com.bwf.core.beans.PropertyValue;

public class test {
    public static void main(String[] args) {
        PropertyValue pv1 = new PropertyValue("a1", "aaa", 1);
        PropertyValue pv2 = new PropertyValue("a2", "bbb", 1);
        PropertyValue pv3 = new PropertyValue("a3", "ccc", 26);
        PropertyValue pv4 = new PropertyValue("a4", "ddd", 65);

        GroovyChainHandler groovyChainHandler = new GroovyChainHandler(pv1);
        JSChainHandler jsChainHandler = new JSChainHandler(pv2);
        LUAChainHandler luaChainHandler = new LUAChainHandler(pv3);
        groovyChainHandler.setNext(jsChainHandler).setNext(luaChainHandler);
//        jsChainHandler


        groovyChainHandler.chainHandle(null);

    }
}
