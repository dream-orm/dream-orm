package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

/**
 * 增删改查语法解析器
 */
public class DMLExpr extends SqlExpr {
    private Statement statement;

    public DMLExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        super(exprReader, myFunctionFactory);
        setExprTypes(ExprType.SELECT, ExprType.INSERT, ExprType.UPDATE, ExprType.DELETE, ExprType.REPLACE);
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        QueryExpr queryExpr = new QueryExpr(exprReader, myFunctionFactory);
        statement = queryExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInsert(ExprInfo exprInfo) throws AntlrException {
        InsertExpr insertExpr = new InsertExpr(exprReader, myFunctionFactory);
        statement = insertExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUpdate(ExprInfo exprInfo) throws AntlrException {
        UpdateExpr updateExpr = new UpdateExpr(exprReader, myFunctionFactory);
        statement = updateExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDelete(ExprInfo exprInfo) throws AntlrException {
        DeleteExpr deleteExpr = new DeleteExpr(exprReader, myFunctionFactory);
        statement = deleteExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprReplace(ExprInfo exprInfo) throws AntlrException {
        ReplaceIntoExpr replaceIntoExpr = new ReplaceIntoExpr(exprReader, myFunctionFactory);
        statement = replaceIntoExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return statement;
    }
}
