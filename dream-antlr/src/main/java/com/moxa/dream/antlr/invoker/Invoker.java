package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public interface Invoker {
    default void init(Assist assist) {

    }

    String invoke(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException;

    Handler[] handlers();

    boolean isAccessible();

    void setAccessible(boolean accessible);
}
