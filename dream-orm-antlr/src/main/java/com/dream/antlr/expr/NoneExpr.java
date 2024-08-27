package com.dream.antlr.expr;

import com.dream.antlr.config.ExprType;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

public class NoneExpr extends SqlExpr {
    public NoneExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.NIL);
    }

    @Override
    protected Statement nil() {
        return null;
    }
}