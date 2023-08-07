package com.dream.template.annotation.validate;

import com.dream.template.annotation.Validated;
import com.dream.template.validate.SizeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(SizeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Size {
    String msg();

    int min() default -1;

    int max() default -1;
}
