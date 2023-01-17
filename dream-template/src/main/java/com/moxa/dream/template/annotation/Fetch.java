package com.moxa.dream.template.annotation;

import com.moxa.dream.template.fetch.Fetcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Fetch {
    Class<? extends Fetcher> value();
}
