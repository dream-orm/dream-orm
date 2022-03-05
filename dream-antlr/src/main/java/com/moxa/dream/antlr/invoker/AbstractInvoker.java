package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public abstract class AbstractInvoker implements Invoker {
    private boolean accessible = true;

    @Override
    public Handler[] handler() {
        return new Handler[0];
    }

    @Override
    public String invoke(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        before(invokerList);
        String sql = invoker(invokerStatement, assist, toSQL, invokerList);
        after(invokerList);
        return sql;
    }

    protected void before(List<Invoker> invokerList) {
        invokerList.add(0, this);
    }

    protected abstract String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException;

    protected void after(List<Invoker> invokerList) {
        invokerList.remove(this);
        if (!this.isAccessible()) {
            this.setAccessible(true);
        }
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
