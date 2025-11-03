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
}
