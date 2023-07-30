package com.moxa.dream.antlr.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;
import java.util.Queue;

public interface Handler {

    /**
     * 翻译抽象树前的操作，列如修改抽象树
     *
     * @param statement    字符串SQL对应的抽象树
     * @param assist       翻译辅助工具
     * @param toSQL        翻译目标SQL的方言
     * @param handlerQueue 当前抽象树应用的所有@函数处理器
     * @param invokerList  正在执行当前抽象树的所有@函数
     * @return 最终需要翻译的抽象树
     * @throws AntlrException
     */
    Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, Queue<Handler> handlerQueue, List<Invoker> invokerList) throws AntlrException;

    /**
     * 翻译抽象树后的操作
     *
     * @param statement 字符串SQL对应的抽象树
     * @param assist    翻译辅助工具
     * @param sql       翻译后的目标SQL
     * @return 最终执行的SQL
     * @throws AntlrException
     */
    String handlerAfter(Statement statement, Assist assist, String sql) throws AntlrException;

}
