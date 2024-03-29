package com.dream.boot.share;

import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ShareSelector.class)
public @interface EnableShare {
    Class<? extends DataSource> value();
}
