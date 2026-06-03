package com.dream.system.typehandler.annotation;

import com.dream.system.typehandler.handler.TypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TypeHandlerProvider {
    Class<?extends TypeHandler>value();
}
