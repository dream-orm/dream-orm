package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.Constant;
import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class SymbolExpr extends SqlExpr {
    SymbolStatement symbolStatement = null;

    public SymbolExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(Constant.SYMBOL);
    }

    @Override
    public Statement nil() {
        return symbolStatement;
    }

    @Override
    protected Statement exprInt(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.IntStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLong(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.LongStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFloat(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.FloatStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDouble(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.DoubleStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprBoolean(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.BooleanStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.LetterStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStr(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.StrStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSkip(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.SKipStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMark(ExprInfo exprInfo) {
        push();
        symbolStatement = new SymbolStatement.MarkStatement();
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
