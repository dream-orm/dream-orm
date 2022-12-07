package com.moxa.dream.drive.annotation;

import com.moxa.dream.util.common.NullObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapper {
    Class<?> value() default NullObject.class;
}
