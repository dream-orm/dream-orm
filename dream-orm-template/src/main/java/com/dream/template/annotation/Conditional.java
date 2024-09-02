package com.dream.template.annotation;

import com.dream.template.condition.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Conditional {

    /**
     * 指定字段名，为空则自动指定
     *
     * @return
     */
    String column() default "";

    /**
     * 是否空过滤
     *
     * @return
     */
    boolean nullFlag() default true;

    /**
     * 是否用or
     *
     * @return
     */
    boolean or() default false;

    /**
     * 条件
     *
     * @return
     */
    Class<? extends Condition> value();
}
