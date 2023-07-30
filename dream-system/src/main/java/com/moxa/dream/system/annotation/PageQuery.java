package com.moxa.dream.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页查询标识
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageQuery {
    /**
     * Page地址
     *
     * @return
     */
    String value() default "page";
}
