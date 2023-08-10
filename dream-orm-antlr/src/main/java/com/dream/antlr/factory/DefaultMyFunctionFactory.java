package com.dream.antlr.factory;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public class DefaultMyFunctionFactory implements MyFunctionFactory {
    @Override
    public MyFunctionStatement create(String function) {
        return new DefaultMyFunctionStatement(function);
    }

    public class DefaultMyFunctionStatement extends MyFunctionStatement {
        private String functionName;

        public DefaultMyFunctionStatement(String functionName) {
            this.functionName = functionName;
        }

        @Override
        public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
            return functionName + "(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
        }
    }
}
