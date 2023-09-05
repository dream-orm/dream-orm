package com.dream.template.annotation.validate;

import com.dream.template.annotation.Validated;
import com.dream.template.validate.NotExistValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(NotExistValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotExist {
    String table() default "";

    String column() default "";

    String msg();
}
