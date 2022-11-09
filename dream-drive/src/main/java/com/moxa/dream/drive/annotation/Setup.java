package com.moxa.dream.drive.annotation;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.util.common.NullObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Setup {
    Class<? extends Collection> rowType() default NullObject.class;

    Class<?> colType() default NullObject.class;

    String[] columnNames() default {};

    boolean cache() default true;

    Command command() default Command.NONE;

    int timeOut() default 0;
}
