package com.bwf.core.beans.proxy.groovy;

import com.bwf.core.beans.PropertyValue;
import com.bwf.core.beans.proxy.NodeProxy;
import com.bwf.core.exception.NodeRunTimeException;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.util.concurrent.locks.ReentrantLock;

public class GroovyNodeProxy implements NodeProxy {
    private PropertyValue executePropertyValue;
    private NodeProxy nextHandler;
    private Script script;
    private static GroovyShell groovyShell;
    private final ReentrantLock lock = new ReentrantLock();

    static {
        groovyShell = new GroovyShell();
    }

    public GroovyNodeProxy(PropertyValue propertyValue) {
        doInit(propertyValue);
    }
    @Override
    public NodeProxy setNext(NodeProxy handler) {
        return this.nextHandler = handler;
    }

    @Override
    public void invoke(Object object) {
        try{
            Object result = InvokerHelper.invokeMethod(script, "invokeMethodA", new Object[]{"abc"});
            if (result == null) {

            }
            System.out.println("【groovy】" + executePropertyValue.getValue());
        }catch (Exception e){
            System.out.println(new NodeRunTimeException("【groovy-"+ executePropertyValue.getName() +"】节点运行时异常 \n【异常信息】"+ e));
        }

        if(nextHandler != null){
            nextHandler.invoke(object);
        }
    }

    @Override
    public void doInit(PropertyValue propertyValue) {
        doCreateScript(propertyValue);
    }

    @Override
    public void updatePV(PropertyValue newPropertyValue) {
        //版本比较
        if(!newPropertyValue.getVersion().equals(executePropertyValue.getVersion())){

        }
        doCreateScript(newPropertyValue);
    }

    private void doCreateScript(PropertyValue propertyValue){
        lock.lock();
        try {
            String gscript = new StringBuilder()
                    .append("def invokeMethodA(record) { \r\n")
                    .append(propertyValue.getValue())
                    .append("\r\n")
                    .append("}\r\n")
                    .toString();
            script = groovyShell.parse(gscript);
            this.executePropertyValue = propertyValue;
        } finally {
            lock.unlock();
        }
    }
}
