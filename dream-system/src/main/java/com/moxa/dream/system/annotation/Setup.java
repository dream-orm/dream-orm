package com.moxa.dream.system.annotation;

import com.moxa.dream.util.common.NullObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Setup {
    Class<? extends Collection> rowType() default NullObject.class;

    Class<?> colType() default NullObject.class;

    boolean cache() default true;

    int timeOut() default 0;
}
