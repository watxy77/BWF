package com.bwf.common.annotation.bootstrap.annotation.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BWFAspect {

    /**
     * @return the per clause expression, defaults to singleton aspect.
     * Valid values are "" (singleton), "perthis(...)", etc
     */
    String value() default "";
}
