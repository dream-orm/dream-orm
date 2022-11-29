package com.moxa.dream.antlr.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;
import java.util.Queue;

public interface Handler {

    Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, Queue<Handler> handlerQueue, List<Invoker> invokerList) throws AntlrException;

    String handlerAfter(Statement statement, Assist assist, String sql) throws AntlrException;

}
