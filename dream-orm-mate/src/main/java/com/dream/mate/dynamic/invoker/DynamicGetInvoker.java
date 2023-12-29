package com.dream.mate.dynamic.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.dynamic.inject.DynamicHandler;

import java.util.List;

public class DynamicGetInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_dynamic_get";
    private DynamicHandler dynamicHandler;

    public DynamicGetInvoker(DynamicHandler dynamicHandler) {
        this.dynamicHandler = dynamicHandler;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        String table = ((SymbolStatement) columnList[0]).getValue();
        return dynamicHandler.process(table);
    }

    @Override
    public Invoker newInstance() {
        return this;
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}
