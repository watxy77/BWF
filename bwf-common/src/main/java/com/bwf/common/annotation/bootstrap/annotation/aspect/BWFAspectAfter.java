package com.bwf.common.annotation.bootstrap.annotation.aspect;

public @interface BWFAspectAfter {
    String value();
    String argNames() default "";
}
