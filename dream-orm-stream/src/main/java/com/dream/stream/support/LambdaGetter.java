package com.dream.stream.support;

import java.io.Serializable;

@FunctionalInterface
public interface LambdaGetter<T> extends Serializable {
    Object get(T source);
}