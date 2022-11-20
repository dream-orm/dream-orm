package com.moxa.dream.system.core.executor;

import java.sql.SQLException;

@FunctionalInterface
interface Function<T, R> {
    R apply(T t) throws SQLException;
}