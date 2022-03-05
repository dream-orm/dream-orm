package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.Statement;

public class BraceExpr extends HelperExpr {
    private BraceStatement brace = new BraceStatement();

    public BraceExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.LBRACE);
    }


    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.HELP, ExprType.SELECT);
        return expr();
    }


    @Override
    protected Statement exprRBrace(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) {
        QueryExpr queryExpr = new QueryExpr(exprReader);
        brace.setStatement(queryExpr.expr());
        setExprTypes(ExprType.RBRACE);
        return expr();
    }

    @Override
    public Statement nil() {
        return brace;
    }

    @Override
    public Statement exprHelp(Statement statement) {
        brace.setStatement(statement);
        setExprTypes(ExprType.RBRACE);
        return expr();
    }
}
