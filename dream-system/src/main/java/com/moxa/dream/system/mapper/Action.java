package com.moxa.dream.system.mapper;

import com.moxa.dream.system.core.executor.Executor;

public interface Action {
    void doAction(Executor executor, Object arg) throws Exception;
}
