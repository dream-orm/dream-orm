package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 方言公共类
 */
public abstract class ToPubSQL extends ToNativeSQL {

    @Override
    protected String before(Statement statement) {
        return statement.getQuickValue();
    }

    @Override
    protected void after(Statement statement, String sql) {
        if (statement.isNeedCache()) {
            statement.setQuickValue(sql);
        }
    }

    @Override
    protected String toString(AliasStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement alias = statement.getAlias();
        if (alias instanceof SymbolStatement.StrStatement) {
            alias = new SymbolStatement.SingleMarkStatement(((SymbolStatement.StrStatement) alias).getValue());
        }
        return toStr(statement.getColumn(), assist, invokerList) + (statement.isShowAlias() ? " AS " : " ") + toStr(alias, assist, invokerList);
    }

    @Override
    protected String toString(InvokerStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Invoker invoker = assist.getInvoker(statement.getFunction(), statement.getNamespace());
        if (invokerList == null) {
            invokerList = new ArrayList<>();
        }
        return invoker.invoke(statement, assist, this, invokerList);
    }

    @Override
    protected String toString(MyFunctionStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return statement.toString(this, assist, invokerList);
    }
}
