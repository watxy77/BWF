package com.bwf.common.annotation.bootstrap;

import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@TypeQualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Nonnull {
    When when() default When.ALWAYS;

    public static class Checker implements TypeQualifierValidator<javax.annotation.Nonnull> {
        public Checker() {
        }

        public When forConstantValue(javax.annotation.Nonnull qualifierqualifierArgument, Object value) {
            return value == null ? When.NEVER : When.ALWAYS;
        }
    }
}
