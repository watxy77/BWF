package com.bwf.engine.BWFNode;

import com.bwf.core.beans.factory.annotation.BWFAutowired;
import com.bwf.common.annotation.bootstrap.annotation.BWFNode;

@BWFNode
public class node_C {
    @BWFAutowired
    private node_A nodeA;
    public void text(){
        System.out.println("ccccc------>"+ nodeA);
    }
}
