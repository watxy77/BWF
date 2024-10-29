package com.bwf.core.beans.resource;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import com.bwf.core.beans.reader.BeanReaderEnum;

public class EncodedResource extends AbstractResource {
    @Nullable
    private String[] path;
    @Nullable
    private int type;
    private Class<?> targetClass;
    private String annotationClassName;


    public EncodedResource(@Nullable String[] path, @Nullable String annotationClassName, Class<?> targetClass) {
        this.path = path;
        this.annotationClassName = annotationClassName;
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

    public String getAnnotationClassName() {
        return annotationClassName;
    }

    public void setAnnotationClassName(String annotationClassName) {
        this.annotationClassName = annotationClassName;
    }
}
