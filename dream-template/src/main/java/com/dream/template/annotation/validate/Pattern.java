package com.dream.template.annotation.validate;

import com.dream.template.annotation.Validated;
import com.dream.template.validate.PatternValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated(PatternValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Pattern {
    String msg();

    String regex();
}
