package com.bwf.common.annotation.bootstrap.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author bjweijiannan
 * @descript@GlobalConfigScan({"engine.yml"})ion
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BWFScope {
    String value();
}
