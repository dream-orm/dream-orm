package com.moxa.dream.system.annotation;


import com.moxa.dream.system.extractor.Extractor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入java字段值后的操作
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Extract {
    /**
     * 操作的Extractor接口
     *
     * @return
     */
    Class<? extends Extractor> value();
}
