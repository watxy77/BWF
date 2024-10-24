package com.bwf.engine;

import com.bwf.common.annotation.bootstrap.annotation.*;
import com.bwf.core.context.BWFApplicationContext;
import com.bwf.core.context.ConfigurableApplicationContext;
import com.bwf.engine.BWFComponent.A;
import com.bwf.engine.BWFComponent.B;
import com.bwf.engine.BWFComponent.C;
import com.bwf.engine.BWFNode.node_C;

@BWFApplication
@BWFGlobalConfigScan({"engine.yml"})
@BWFConfigBeanXML("")
@BWFConfigBeanYAML("")
@BWFConfigBeanJSON("json/abc.json")
public class BWFEngineApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = BWFApplicationContext.run(BWFEngineApplication.class, args);
//        A bean = (A) run.getComponentBean("A");
//        bean.text();
//        B bean1 = (B) run.getComponentBean("B");
//        bean1.text();
//        C bean2 = (C) run.getComponentBean("C");
//        bean2.text();
//        node_C bean3 = (node_C) run.getNodeBean("node_C");
//        bean3.text();

    }
}
