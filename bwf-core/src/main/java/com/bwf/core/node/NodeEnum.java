package com.bwf.core.node;
/**
 * @Author bjweijiannan
 * @description
 */
public enum NodeEnum {
    NODE_AI(1),
    NODE_AVIATOR(2),
    NODE_GROOVY(3),
    NODE_JAVA(4),
    NODE_JS(5),
    NODE_LUA(6),
    NODE_PYTHON(7),
    NODE_QLEXPRESS(8);
    private int code;
    private NodeEnum(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

}
