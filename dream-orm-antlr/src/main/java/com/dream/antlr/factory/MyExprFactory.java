package com.dream.antlr.factory;

import com.dream.antlr.expr.SqlExpr;
import com.dream.antlr.read.ExprReader;

public interface MyExprFactory {
    SqlExpr newExpr(ExprReader exprReader);
}
