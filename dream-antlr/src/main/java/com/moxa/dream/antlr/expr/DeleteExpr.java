package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class DeleteExpr extends SqlExpr {
    private final DeleteStatement deleteStatement = new DeleteStatement();

    public DeleteExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.DELETE);
    }

    @Override
    protected Statement exprDelete(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.LETTER);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) {
        push();
        deleteStatement.setTable(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.NIL, ExprType.WHERE);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader);
        Statement statement = operTreeExpr.expr();
        deleteStatement.setCondition(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return deleteStatement;
    }
}
