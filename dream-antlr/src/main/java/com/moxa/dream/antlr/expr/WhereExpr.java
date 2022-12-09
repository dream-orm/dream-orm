package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.WhereStatement;

public class WhereExpr extends SqlExpr {
    private final WhereStatement whereStatement = new WhereStatement();

    public WhereExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.WHERE);
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) throws AntlrException {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader);
        Statement statement = operTreeExpr.expr();
        whereStatement.setCondition(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return whereStatement;
    }
}
