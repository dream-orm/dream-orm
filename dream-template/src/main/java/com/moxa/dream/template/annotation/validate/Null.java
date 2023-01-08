package com.moxa.dream.template.annotation.validate;

import com.moxa.dream.template.annotation.Validated;
import com.moxa.dream.template.validate.NullValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(NullValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Null {
    String msg();
}
