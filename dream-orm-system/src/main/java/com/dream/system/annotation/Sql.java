package com.dream.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sql {
    /**
     * 接口方法对应的SQL语句
     *
     * @return
     */
    String value();

    /**
     * 超时时长，只应用于查询
     *
     * @return
     */
    int timeOut() default 0;
}
