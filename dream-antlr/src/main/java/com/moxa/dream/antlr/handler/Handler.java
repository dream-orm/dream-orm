package com.moxa.dream.antlr.handler;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;
import java.util.Queue;

public interface Handler {
    /**
     * 执行前
     *
     * @param statement    检测的抽象树
     * @param assist
     * @param toSQL
     * @param handlerQueue 只有加入当前SqlHandler，才会执行handlerAfter
     * @param invokerList
     * @return
     * @throws InvokerException
     */
    Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, Queue<Handler> handlerQueue, List<Invoker> invokerList) throws InvokerException;

    /**
     * 执行后
     *
     * @param assist
     * @param sql    解析后的sql语句
     * @return
     * @throws InvokerException
     */
    String handlerAfter(ToAssist assist, String sql) throws InvokerException;

}
