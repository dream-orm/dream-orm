package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.HavingStatement;
import com.moxa.dream.antlr.smt.Statement;

public class HavingExpr extends SqlExpr {
    private final HavingStatement havingStatement = new HavingStatement();

    public HavingExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.HAVING);
    }

    @Override
    protected Statement exprHaving(ExprInfo exprInfo) throws AntlrException {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader);
        havingStatement.setCondition(operTreeExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return havingStatement;
    }
}
