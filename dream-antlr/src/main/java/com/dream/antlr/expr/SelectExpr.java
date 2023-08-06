package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.Statement;

/**
 * 查询语法解析器
 */
public class SelectExpr extends HelperExpr {
    private final SelectStatement selectStatement = new SelectStatement();

    public SelectExpr(ExprReader exprReader) {
        this(exprReader, () -> new ListColumnExpr(exprReader, () -> new AliasColumnExpr(exprReader),
                new ExprInfo(ExprType.COMMA, ",")));
    }

    public SelectExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.SELECT);
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.DISTINCT, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprDistinct(ExprInfo exprInfo) throws AntlrException {
        push();
        selectStatement.setDistinct(true);
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    public Statement nil() {
        return selectStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        selectStatement.setSelectList((ListColumnStatement) statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
