package com.dream.tdengine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Tag {
    /**
     * 对应的数据库表标签字段名称
     *
     * @return
     */
    String value()default "";

    /**
     * 对应的数据库表标签字段类型
     *
     * @return
     */
    int jdbcType() default Types.NULL;
}
