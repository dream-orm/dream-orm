package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

/**
 * 增删改查语法解析器
 */
public class DDLExpr extends SqlExpr {
    private Statement statement;

    public DDLExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.CREATE, ExprType.ALTER, ExprType.TRUNCATE, ExprType.DROP);
    }

    @Override
    protected Statement exprCreate(ExprInfo exprInfo) throws AntlrException {
        DDLCreateExpr ddlCreateExpr = new DDLCreateExpr(exprReader);
        this.statement = ddlCreateExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAlter(ExprInfo exprInfo) throws AntlrException {
        DDLAlterExpr ddlCreateExpr = new DDLAlterExpr(exprReader);
        this.statement = ddlCreateExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprTruncate(ExprInfo exprInfo) throws AntlrException {
        DDLTruncateExpr ddlCreateExpr = new DDLTruncateExpr(exprReader);
        this.statement = ddlCreateExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
        DDLDropExpr ddlCreateExpr = new DDLDropExpr(exprReader);
        this.statement = ddlCreateExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return statement;
    }
}
