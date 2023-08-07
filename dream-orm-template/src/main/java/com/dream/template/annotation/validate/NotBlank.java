package com.dream.template.annotation.validate;

import com.dream.template.annotation.Validated;
import com.dream.template.validate.NotBlankValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(NotBlankValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotBlank {
    String msg();
}
