package com.bwf.common.annotation.bootstrap.annotation;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CheckForNull
@Nonnull(
        when = When.MAYBE
)
@TypeQualifierNickname
public @interface Nullable {
}