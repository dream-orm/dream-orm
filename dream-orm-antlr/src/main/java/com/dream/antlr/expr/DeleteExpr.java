package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DeleteStatement;
import com.dream.antlr.smt.Statement;

/**
 * 删除语法解析器
 */
public class DeleteExpr extends HelperExpr {
    private final DeleteStatement deleteStatement = new DeleteStatement();

    public DeleteExpr(ExprReader exprReader) {
        this(exprReader, () -> new AliasColumnExpr(exprReader));
    }

    public DeleteExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.DELETE);
    }

    @Override
    protected Statement exprDelete(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) throws AntlrException {
        WhereExpr whereExpr = new WhereExpr(exprReader);
        Statement statement = whereExpr.expr();
        deleteStatement.setWhere(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return deleteStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        deleteStatement.setTable(statement);
        setExprTypes(ExprType.NIL, ExprType.WHERE);
        return expr();
    }
}
