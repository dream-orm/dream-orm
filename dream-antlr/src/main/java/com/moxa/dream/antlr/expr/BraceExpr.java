package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.Statement;

public class BraceExpr extends HelperExpr {
    private final BraceStatement brace = new BraceStatement();

    public BraceExpr(ExprReader exprReader) {
        this(exprReader, () -> new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ",")));

    }

    public BraceExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.LBRACE);
    }


    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP, ExprType.SELECT);
        return expr();
    }


    @Override
    protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
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
    protected Statement exprHelp(Statement statement) throws AntlrException {
        brace.setStatement(statement);
        setExprTypes(ExprType.RBRACE);
        return expr();
    }
}
