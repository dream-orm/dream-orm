package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public interface Invoker {
    String DEFAULT_NAMESPACE = "default";

    default void init(Assist assist) {

    }

    String invoke(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException;

    Handler[] handlers();

    boolean isAccessible();

    void setAccessible(boolean accessible);


    Invoker newInstance();

    default String namespace() {
        return DEFAULT_NAMESPACE;
    }

    String function();
}
