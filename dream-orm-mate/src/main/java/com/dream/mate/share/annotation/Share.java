package com.dream.mate.share.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Share {
    /**
     * 选择使用的多数据源
     *
     * @return 数据源名称
     */
    String value();
}
