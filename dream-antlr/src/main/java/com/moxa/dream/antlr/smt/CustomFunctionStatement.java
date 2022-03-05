package com.moxa.dream.antlr.smt;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.CompareExpr;
import com.moxa.dream.antlr.expr.HelperExpr;
import com.moxa.dream.antlr.expr.ListColumnExpr;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public abstract class CustomFunctionStatement extends FunctionStatement {

    @Override
    public int getNameId() {
        return CustomFunctionStatement.class.getSimpleName().hashCode();
    }

    public abstract String toString(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    public HelperExpr.Helper getHelper(ExprReader exprReader) {
        return () -> getListExpr(exprReader);
    }

    public ListColumnExpr getListExpr(ExprReader exprReader) {
        return new ListColumnExpr(exprReader, () -> new CompareExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
    }
}
