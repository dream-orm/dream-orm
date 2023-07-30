package com.moxa.dream.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 视图标识
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface View {
    /**
     * 对应的数据库表映射的java类
     *
     * @return
     */
    Class<?> value();
}
