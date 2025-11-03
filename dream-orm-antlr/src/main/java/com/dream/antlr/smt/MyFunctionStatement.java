package com.dream.antlr.smt;

import com.dream.antlr.config.Assist;
import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.CompareExpr;
import com.dream.antlr.expr.HelperExpr;
import com.dream.antlr.expr.ListColumnExpr;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public abstract class MyFunctionStatement extends FunctionStatement {

    @Override
    public final int getNameId() {
        return MyFunctionStatement.class.getSimpleName().hashCode();
    }

    public abstract String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException;

    public HelperExpr.Helper getHelper(ExprReader exprReader) {
        return () -> new ListColumnExpr(exprReader, () -> new CompareExpr(exprReader, null), new ExprInfo(ExprType.COMMA, ","), null);
    }
}
