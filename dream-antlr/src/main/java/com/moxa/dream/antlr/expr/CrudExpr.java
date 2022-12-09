package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;

public class CrudExpr extends SqlExpr {
    private Statement statement;

    public CrudExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.SELECT, ExprType.INSERT, ExprType.UPDATE, ExprType.DELETE);
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        QueryExpr queryExpr = new QueryExpr(exprReader);
        statement = queryExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInsert(ExprInfo exprInfo) throws AntlrException {
        InsertExpr insertExpr = new InsertExpr(exprReader);
        statement = insertExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUpdate(ExprInfo exprInfo) throws AntlrException {
        UpdateExpr updateExpr = new UpdateExpr(exprReader);
        statement = updateExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDelete(ExprInfo exprInfo) throws AntlrException {
        DeleteExpr deleteExpr = new DeleteExpr(exprReader);
        statement = deleteExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return statement;
    }
}
