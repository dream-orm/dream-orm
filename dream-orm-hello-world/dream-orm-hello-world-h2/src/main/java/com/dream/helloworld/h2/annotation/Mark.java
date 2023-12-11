package com.dream.helloworld.h2.annotation;

import com.dream.helloworld.h2.MaskProcessor;
import com.dream.system.annotation.Processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Processor(MaskProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mark {
    int startPos();
    int endPos();
}
