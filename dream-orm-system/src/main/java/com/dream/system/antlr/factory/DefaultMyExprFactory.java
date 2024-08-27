package com.dream.system.antlr.factory;

import com.dream.antlr.expr.SqlExpr;
import com.dream.antlr.factory.MyExprFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.system.antlr.expr.ColonExpr;

public class DefaultMyExprFactory implements MyExprFactory {
    @Override
    public SqlExpr newExpr(ExprReader exprReader) {
        return new ColonExpr(exprReader);
    }
}
