package com.dream.template.annotation.validate;

import com.dream.template.annotation.Validated;
import com.dream.template.validate.RangeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(RangeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Length {
    String msg();

    int min() default -1;

    int max() default -1;
}
