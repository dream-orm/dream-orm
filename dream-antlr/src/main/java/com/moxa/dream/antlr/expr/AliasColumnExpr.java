package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.AliasStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class AliasColumnExpr extends HelperExpr {
    AliasStatement aliasStatement = new AliasStatement();

    public AliasColumnExpr(ExprReader exprReader) {
        this(exprReader, () -> new CompareExpr(exprReader));
        setExprTypes(ExprType.HELP, ExprType.STAR);
    }

    public AliasColumnExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
    }

    @Override
    public Statement exprHelp(Statement statement) {
        aliasStatement.setColumn(statement);
        setExprTypes(ExprType.LETTER, ExprType.AS, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) {
        push();
        aliasStatement.setAlias(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStar(ExprInfo exprInfo) {
        push();
        aliasStatement.setColumn(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAs(ExprInfo exprInfo) {
        push();
        aliasStatement.setShowAlias(true);
        setExprTypes(ExprType.LETTER);
        return expr();
    }

    @Override
    public Statement nil() {
        if (aliasStatement.getAlias() == null)
            return aliasStatement.getColumn();
        else
            return aliasStatement;
    }
}
