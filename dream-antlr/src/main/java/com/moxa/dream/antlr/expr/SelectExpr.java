package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.SelectStatement;
import com.moxa.dream.antlr.smt.Statement;

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
