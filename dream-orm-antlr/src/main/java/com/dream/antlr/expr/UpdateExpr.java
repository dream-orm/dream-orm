package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.UpdateStatement;

/**
 * 更新语法解析器
 */
public class UpdateExpr extends HelperExpr {
    private final UpdateStatement updateStatement = new UpdateStatement();

    public UpdateExpr(ExprReader exprReader) {
        this(exprReader, () -> new AliasColumnExpr(exprReader));
    }

    public UpdateExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.UPDATE);
    }

    @Override
    protected Statement exprUpdate(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    protected Statement exprSet(ExprInfo exprInfo) throws AntlrException {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ","));
        updateStatement.setConditionList(listColumnExpr.expr());
        setExprTypes(ExprType.WHERE, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) throws AntlrException {
        WhereExpr whereExpr = new WhereExpr(exprReader);
        Statement statement = whereExpr.expr();
        updateStatement.setWhere(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return updateStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        updateStatement.setTable(statement);
        setExprTypes(ExprType.SET);
        return expr();
    }
}
