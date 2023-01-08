package com.moxa.dream.template.annotation.validate;

import com.moxa.dream.template.annotation.Validated;
import com.moxa.dream.template.validate.MaxValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(MaxValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Max {
    String msg();

    double value();
}
