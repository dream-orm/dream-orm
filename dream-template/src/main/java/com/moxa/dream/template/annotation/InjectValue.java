package com.moxa.dream.template.annotation;

import com.moxa.dream.template.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectValue {
    Class<? extends Value> value();

    InjectType type() default InjectType.INSERT_UPDATE;
}
