package com.dream.tdengine.statement;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public class TdIntervalStatement extends MyFunctionStatement {
    @Override
    public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return " INTERVAL(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
    }
}
