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
        BraceExpr braceExpr = new BraceExpr(exprReader);
        Statement statement = braceExpr.expr();
        insertStatement.setParams(statement);
        setExprTypes(ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    @Override
    protected Statement exprValues(ExprInfo exprInfo) {
        push();
        Statement statement = new ListColumnExpr(exprReader,new ExprInfo(ExprType.COMMA,",")).expr();
        insertStatement.setValues(new InsertStatement.ValuesStatement(statement));
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
    protected Statement exprInvoker(ExprInfo exprInfo) {
        ColumnExpr columnExpr=new ColumnExpr(exprReader);
        Statement statement = columnExpr.expr();
        insertStatement.setValues(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return insertStatement;
    }

}
