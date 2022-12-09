package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;

public interface DialectFactory {

    MappedStatement compile(MethodInfo methodInfo, Object arg) throws Exception;

    DbType getDbType();

    void addInvoker(String invokerName, Invoker invoker);
}
