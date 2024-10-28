package com.bwf.common.annotation.bootstrap.annotation.aspect;

public @interface BWFAspectBefore {
    String value();
    String argNames() default "";
}
