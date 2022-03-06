package com.moxa.dream.antlr.handler;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;
import java.util.Queue;

public interface Handler {

    Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, Queue<Handler> handlerQueue, List<Invoker> invokerList) throws InvokerException;

    String handlerAfter(ToAssist assist, String sql) throws InvokerException;

}
