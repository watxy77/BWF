package com.bwf.core.eventbus.model;
/**
 * @Author bjweijiannan
 * @description
 */
public enum EventEnum {
    NODE_HANDLE_SUB("NODE_HANDLE_SUB"),
    DATA_HANDLE_SUB("DATA_HANDLE_SUB"),
    JIMDB_CLUSTER_HANDLE_SUB("JIMDB_CLUSTER_HANDLE_SUB"),
    RATIO_FID_SUB("RATIO_FID_SUB"),
    DUCC_HANDLE_SUB("DUCC_HANDLE_SUB");
    private String code;
    private EventEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}
