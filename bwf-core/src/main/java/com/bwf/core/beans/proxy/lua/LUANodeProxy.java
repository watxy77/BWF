package com.bwf.core.beans.proxy.lua;

import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.proxy.NodeProxy;
import groovy.lang.GroovyShell;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LUANodeProxy implements NodeProxy {
    private PropertyValue propertyValue;
    private NodeProxy nextHandler;
    private static Globals globals;
    private  LuaValue transcoderObj;

    static {
        globals = JsePlatform.standardGlobals();
    }


    public LUANodeProxy(PropertyValue propertyValue) {
        doInit(propertyValue);
    }

    @Override
    public NodeProxy setNext(NodeProxy handler) {
        return this.nextHandler = handler;
    }

    @Override
    public void invoke(Object object) {
        System.out.println("【lua】" + propertyValue.getValue());
        LuaValue func = transcoderObj.get(LuaValue.valueOf("test"));
        String result = func.call(LuaValue.valueOf("sky")).toString();
        if(nextHandler != null){
            nextHandler.invoke(object);
        }
    }

    @Override
    public void doInit(PropertyValue propertyValue) {
        doCreateScript(propertyValue);
        this.propertyValue = propertyValue;

    }

    @Override
    public void updatePV(PropertyValue propertyValue) {

    }

    private void doCreateScript(PropertyValue propertyValue){
        transcoderObj = globals.load(propertyValue.getValue().toString());
    }
}
