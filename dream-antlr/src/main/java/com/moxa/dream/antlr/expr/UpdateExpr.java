package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.smt.UpdateStatement;

public class UpdateExpr extends SqlExpr {
    private UpdateStatement updateStatement = new UpdateStatement();

    public UpdateExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.UPDATE);
    }

    @Override
    protected Statement exprUpdate(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.LETTER);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) {
        push();
        updateStatement.setTable(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.SET);
        return expr();
    }

    @Override
    protected Statement exprSet(ExprInfo exprInfo) {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> {
            CompareExpr compareExpr = new CompareExpr(exprReader);
            return compareExpr;
        }, new ExprInfo(ExprType.COMMA, ","));
        updateStatement.setConditionList(listColumnExpr.expr());
        setExprTypes(ExprType.WHERE, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader);
        Statement statement = operTreeExpr.expr();
        updateStatement.setWhere(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return updateStatement;
    }
}
