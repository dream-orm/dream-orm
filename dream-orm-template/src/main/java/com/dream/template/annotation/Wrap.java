package com.dream.template.annotation;

import com.dream.template.wrap.RawWrapper;
import com.dream.template.wrap.Wrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Wrap {
    /**
     * 参数修改类
     *
     * @return 参数修改类
     */
    Class<? extends Wrapper> wrapper() default RawWrapper.class;

    /**
     * 参数修改时机：插入、更新、插入或更新，默认插入
     *
     * @return 参数修改时机
     */
    WrapType wrapType() default WrapType.INSERT;

    String value() default "";
}
