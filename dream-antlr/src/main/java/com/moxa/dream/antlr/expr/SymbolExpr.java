package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.Constant;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class SymbolExpr extends SqlExpr {
    Statement statement = null;

    public SymbolExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(Constant.SYMBOL).addExprTypes(ExprType.STAR);
    }

    @Override
    public Statement nil() {
        return statement;
    }

    @Override
    protected Statement exprStar(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.LetterStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInt(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.IntStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLong(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.LongStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFloat(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.FloatStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDouble(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.DoubleStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.LetterStatement(exprInfo.getInfo());
        setExprTypes(ExprType.DOT, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDot(ExprInfo exprInfo) {
        push();
        ListColumnStatement listColumnStatement = (ListColumnStatement) new ListColumnExpr(exprReader, () -> {
            SymbolExpr symbolExpr = new SymbolExpr(exprReader);
            symbolExpr.setExprTypes(ExprType.LETTER, ExprType.STR, ExprType.JAVA_STR, ExprType.STAR, ExprType.SINGLE_MARK);
            return symbolExpr;
        }, new ExprInfo(ExprType.DOT, ".")).expr();
        listColumnStatement.addFirst(statement);
        statement = listColumnStatement;
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStr(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.StrStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprJavaStr(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.JavaStrStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSingleMark(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.SingleMarkStatement(exprInfo.getInfo());
        setExprTypes(ExprType.DOT, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSkip(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.SKipStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMark(ExprInfo exprInfo) {
        push();
        statement = new SymbolStatement.MarkStatement();
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
