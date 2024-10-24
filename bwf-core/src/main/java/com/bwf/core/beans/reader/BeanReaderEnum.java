package com.bwf.core.beans.reader;
/**
 * @Author bjweijiannan
 * @description
 */
public enum BeanReaderEnum {
    BEAN_JSON(1),
    BEAN_XML(2),
    BEAN_YAML(3),
    BEAN_ANNOTATION(4);
    private int code;
    private BeanReaderEnum(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

}
