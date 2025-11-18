package com.dream.system.annotation;

import com.dream.system.action.FetchActionProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Processor(FetchActionProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Fetch {
    /**
     * 自定义sql满足复杂开发
     *
     * @return sql语句
     */
    String value();

    /**
     * 复用第一次编译，查询条件含有@not，@non，@between，@foreach等需要动态计算的条件时，则必须设置成false
     *
     * @return 是否复用第一次编译MethodInfo
     */
    boolean fast() default true;
}
