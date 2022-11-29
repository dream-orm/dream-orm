package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public abstract class AbstractInvoker implements Invoker {
    private boolean accessible = true;
    private Handler[] handlers = null;

    @Override
    public Handler[] handlers() {
        if (handlers == null) {
            handlers = handler();
        }
        return handlers;
    }

    @Override
    public String invoke(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        before(invokerList);
        String sql = invoker(invokerStatement, assist, toSQL, invokerList);
        after(invokerList);
        return sql;
    }

    protected void before(List<Invoker> invokerList) {
        invokerList.add(this);
    }

    protected abstract String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException;

    protected void after(List<Invoker> invokerList) {
        invokerList.remove(this);
        if (!this.isAccessible()) {
            this.setAccessible(true);
        }
    }

    protected Handler[] handler() {
        return new Handler[0];
    }

    @Override
    public boolean isAccessible() {
        return accessible;
    }

    @Override
    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }
}
