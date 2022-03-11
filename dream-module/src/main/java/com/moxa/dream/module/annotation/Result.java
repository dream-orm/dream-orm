package com.moxa.dream.module.annotation;

import com.moxa.dream.module.producer.util.NullObject;

import java.util.Collection;

public @interface Result {
    Class<? extends Collection> rowType() default NullObject.class;

    Class<?> colType() default NullObject.class;

}
