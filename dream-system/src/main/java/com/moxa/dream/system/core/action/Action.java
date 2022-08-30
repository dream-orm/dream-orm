package com.moxa.dream.system.core.action;

import com.moxa.dream.system.core.executor.Executor;

import java.util.Properties;

public interface Action {
    default void setProperties(Properties properties) {

    }

    Object doAction(Executor executor, Object arg) throws Exception;
}
