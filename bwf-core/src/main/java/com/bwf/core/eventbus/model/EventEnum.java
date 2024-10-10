package com.bwf.core.eventbus.model;

public enum EventEnum {
    RULE_HANDLE_SUB("RULE_HANDLE_SUB"),
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
