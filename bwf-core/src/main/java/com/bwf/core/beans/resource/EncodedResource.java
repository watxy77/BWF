package com.bwf.core.beans.resource;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

public class EncodedResource extends AbstractResource {
    @Nullable
    private String[] path;
    @Nullable
    private int type;
    private Class<?> targetClass;


    public EncodedResource(@Nullable String[] path, @Nullable int type, Class<?> targetClass) {
        this.path = path;
        this.type = type;
        this.targetClass = targetClass;
    }

    @Nullable
    public String[] getPath() {
        return path;
    }

    public void setPath(@Nullable String[] path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }
}
