package com.dream.flex.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableFlexAPT {
    /**
     * 生成apt类的后缀
     *
     * @return
     */
    String classSuffix() default "Def";

    /**
     * 生成类所在目录
     * 支持./、../和/
     *
     * @return
     */
    String dir() default "../def";
}
