package com.moxa.dream.antlr.smt;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.expr.CompareExpr;
import com.moxa.dream.antlr.expr.HelperExpr;
import com.moxa.dream.antlr.expr.ListColumnExpr;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public abstract class MyFunctionStatement extends FunctionStatement {

    @Override
    public final int getNameId() {
        return MyFunctionStatement.class.getSimpleName().hashCode();
    }

    public abstract String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException;

    public HelperExpr.Helper getHelper(ExprReader exprReader) {
        return () -> new ListColumnExpr(exprReader, () -> new CompareExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
    }
}
