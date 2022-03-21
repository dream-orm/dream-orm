package com.moxa.dream.module.annotation;

import com.moxa.dream.module.reflect.util.NullObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Result {
    Class<? extends Collection> rowType() default NullObject.class;

    Class<?> colType() default NullObject.class;

    boolean generatedKeys() default false;


}
