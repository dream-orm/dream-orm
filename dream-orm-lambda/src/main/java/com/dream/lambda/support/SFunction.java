package com.dream.lambda.support;

import java.util.function.Function;

@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R> {
}
