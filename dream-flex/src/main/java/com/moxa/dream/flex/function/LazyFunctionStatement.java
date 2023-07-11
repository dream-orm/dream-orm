package com.moxa.dream.flex.function;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.MyFunctionStatement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class LazyFunctionStatement extends MyFunctionStatement {
    private LazyFunction lazyFunction;

    public LazyFunctionStatement(LazyFunction lazyFunction) {
        this.lazyFunction = lazyFunction;
    }

    @Override
    public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toSQL.toStr(lazyFunction.getStatement(), assist, invokerList);
    }
}
