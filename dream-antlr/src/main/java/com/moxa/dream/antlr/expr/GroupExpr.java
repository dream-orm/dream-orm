package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.GroupStatement;
import com.moxa.dream.antlr.smt.Statement;

public class GroupExpr extends SqlExpr {
    private final GroupStatement groupStatement = new GroupStatement();

    public GroupExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.GROUP);
    }

    @Override
    protected Statement exprGroup(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.BY);
        return expr();
    }

    @Override
    protected Statement exprBy(ExprInfo exprInfo) throws AntlrException {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ","));

        groupStatement.setGroup(listColumnExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return groupStatement;
    }
}
