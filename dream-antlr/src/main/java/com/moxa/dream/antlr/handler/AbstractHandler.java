package com.moxa.dream.antlr.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;
import java.util.Queue;

public abstract class AbstractHandler implements Handler {

    private Handler[] boundHandlerList;

    private int life;

    private Handler parentHandler;

    @Override
    public final Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, Queue<Handler> handlerQueue, List<Invoker> invokerList) throws AntlrException {

        if ((parentHandler == null || (!(parentHandler instanceof AbstractHandler)) || ((AbstractHandler) parentHandler).isLife())) {
            if (interest(statement, assist)) {
                life++;
                handlerQueue.add(this);
                statement = handlerBefore(statement, assist, toSQL, invokerList, life);
            }
            if (boundHandlerList == null) {
                boundHandlerList = handlerBound();
                if (boundHandlerList != null && boundHandlerList.length > 0) {
                    for (Handler boundHandler : boundHandlerList) {
                        if (boundHandler instanceof AbstractHandler) {
                            ((AbstractHandler) boundHandler).setParentHandler(this);
                        }
                    }
                }
            }
            if (boundHandlerList != null && boundHandlerList.length > 0 && this.isLife()) {
                for (Handler handler : boundHandlerList) {
                    statement = handler.handlerBefore(statement, assist, toSQL, handlerQueue, invokerList);
                }
            }
        }
        return statement;
    }


    @Override
    public final String handlerAfter(Statement statement, Assist assist, String sql) throws AntlrException {
        life--;
        return handlerAfter(statement, assist, sql, life);
    }

    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        return sql;
    }


    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        return statement;
    }

    protected Handler[] handlerBound() {
        return new Handler[0];
    }

    protected final boolean isLife() {
        return life > 0;
    }

    protected abstract boolean interest(Statement statement, Assist sqlAssist);

    private void setParentHandler(Handler parentHandler) {
        this.parentHandler = parentHandler;
    }
}
