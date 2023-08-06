package com.dream.flex.function;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.sql.ToSQL;

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
