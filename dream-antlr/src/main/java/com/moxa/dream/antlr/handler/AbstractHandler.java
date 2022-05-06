package com.moxa.dream.antlr.handler;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.List;
import java.util.Queue;

public abstract class AbstractHandler implements Handler {

    private Handler[] boundHandlerList;

    private int life;

    private Handler parentHandler;

    @Override
    public final Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, Queue<Handler> handlerQueue, List<Invoker> invokerList) throws InvokerException {

        if ((parentHandler == null || (!(parentHandler instanceof AbstractHandler)) || ((AbstractHandler) parentHandler).isLife())) {

            if (interest(statement, assist)) {
                life++;

                handlerQueue.add(this);

                statement = handlerBefore(statement, assist, toSQL, invokerList, life);
            }

            if (boundHandlerList == null) {
                boundHandlerList = handlerBound();
                if (!ObjectUtil.isNull(boundHandlerList)) {
                    for (Handler boundHandler : boundHandlerList) {
                        if (boundHandler instanceof AbstractHandler) {
                            ((AbstractHandler) boundHandler).setParentHandler(this);
                        }
                    }
                }
            }
            if (!ObjectUtil.isNull(boundHandlerList) && this.isLife()) {
                for (Handler handler : boundHandlerList) {
                    statement = handler.handlerBefore(statement, assist, toSQL, handlerQueue, invokerList);
                }
            }
        }
        return statement;
    }


    @Override
    public final String handlerAfter(ToAssist assist, String sql) throws InvokerException {

        life--;
        return handlerAfter(assist, sql, life);
    }

    protected String handlerAfter(ToAssist assist, String sql, int life) throws InvokerException {
        return sql;
    }


    protected abstract Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException;

    protected Handler[] handlerBound() {
        return new Handler[0];
    }

    protected final boolean isLife() {
        return life > 0;
    }


    protected abstract boolean interest(Statement statement, ToAssist sqlAssist);

    private void setParentHandler(Handler parentHandler) {
        this.parentHandler = parentHandler;
    }
}
