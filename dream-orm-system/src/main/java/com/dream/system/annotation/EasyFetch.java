package com.dream.system.annotation;

import com.dream.system.action.EasyFetchActionProcessor;
import com.dream.system.action.FetchActionProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Processor(EasyFetchActionProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EasyFetch {
    /**
     * 查询的目标表，为空则根据字段属性类型取表名
     *
     * @return
     */
    String table() default "";

    /**
     * 目标条件字段
     *
     * @return
     */
    String column();

    /**
     * 需要查询的目标字段，为空则根据字段属性类型决定查询字段
     * @return
     */
    String[]columns() default {};
    /**
     * 当前对象属性名，取该值和目标条件字段作为查询条件
     *
     * @return
     */
    String field();
}
