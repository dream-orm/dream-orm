package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.UpdateStatement;

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
    protected Statement exprUpdate(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    protected Statement exprSet(ExprInfo exprInfo) {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ","));
        updateStatement.setConditionList(listColumnExpr.expr());
        setExprTypes(ExprType.WHERE, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) {
        push();
        CompareExpr compareExpr = new CompareExpr(exprReader);
        Statement statement = compareExpr.expr();
        updateStatement.setWhere(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return updateStatement;
    }

    @Override
    public Statement exprHelp(Statement statement) {
        updateStatement.setTable(statement);
        setExprTypes(ExprType.SET);
        return expr();
    }
}
