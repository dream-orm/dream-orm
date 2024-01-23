package com.dream.antlr.smt;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public class DefaultMyFunctionStatement extends MyFunctionStatement {
    private String functionName;

    public DefaultMyFunctionStatement(String functionName) {
        setFunctionName(functionName);
    }

    @Override
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return functionName + "(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
    }

    @Override
    public DefaultMyFunctionStatement clone() {
        DefaultMyFunctionStatement defaultMyFunctionStatement = (DefaultMyFunctionStatement) super.clone();
        defaultMyFunctionStatement.setFunctionName(functionName);
        return defaultMyFunctionStatement;
    }
}

