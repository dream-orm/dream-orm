package com.dream.system.annotation;


import com.dream.system.typehandler.handler.ObjectTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;

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
    String value() default "";

    /**
     * 对应的数据库表字段类型
     *
     * @return
     */
    int jdbcType() default Types.NULL;

    /**
     * 自定义类型转换器
     *
     * @return
     */
    Class<? extends TypeHandler> typeHandler() default ObjectTypeHandler.class;
}
