package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class InsertExpr extends SqlExpr {
    private InsertStatement insertStatement = new InsertStatement();

    public InsertExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.INSERT);
    }

    @Override
    protected Statement exprInsert(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.INTO);
        return expr();
    }

    @Override
    protected Statement exprInto(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.LETTER);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) {
        push();
        insertStatement.setTable(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.LBRACE, ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) {
        BraceExpr braceExpr = new BraceExpr(exprReader,
                () -> new ListColumnExpr(exprReader,
                        () -> {
                            ColumnExpr columnExpr = new ColumnExpr(exprReader);
                            columnExpr.setExprTypes(ExprType.LETTER);
                            return columnExpr;
                        }, new ExprInfo(ExprType.COMMA, ",")));
        Statement statement = braceExpr.expr();
        insertStatement.setParams(statement);
        setExprTypes(ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    @Override
    protected Statement exprValues(ExprInfo exprInfo) {
        push();
        BraceExpr braceExpr = new BraceExpr(exprReader,
                () -> new ListColumnExpr(exprReader,
                        () -> new CompareExpr(exprReader),
                        new ExprInfo(ExprType.COMMA, ",")));
        insertStatement.setValues(new InsertStatement.ValuesStatement(braceExpr.expr()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) {
        QueryExpr queryExpr = new QueryExpr(exprReader);
        Statement statement = queryExpr.expr();
        insertStatement.setValues(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return insertStatement;
    }

}
