package com.moxa.dream.system.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

/**
 * 映射java类字段与数据库表字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    /**
     * 对应的数据库表字段名称
     *
     * @return
     */
    String value();

    /**
     * 对应的数据库表字段类型
     *
     * @return
     */
    int jdbcType() default Types.NULL;
}
