package com.moxa.dream.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 映射接口类与数据库表名称
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * 数据库表名称
     *
     * @return
     */
    String value();
}
