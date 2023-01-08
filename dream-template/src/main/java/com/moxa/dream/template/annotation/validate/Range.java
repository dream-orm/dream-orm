package com.moxa.dream.template.annotation.validate;

import com.moxa.dream.template.annotation.Validated;
import com.moxa.dream.template.validate.RangeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(RangeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {
    String msg();

    double min() default -1;

    double max() default -1;
}
