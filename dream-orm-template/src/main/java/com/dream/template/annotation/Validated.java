package com.dream.template.annotation;

import com.dream.template.validate.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Validated {
    /**
     * 校验的注解类
     * @return 校验的注解类
     */
    Class<? extends Validator> value();
}
