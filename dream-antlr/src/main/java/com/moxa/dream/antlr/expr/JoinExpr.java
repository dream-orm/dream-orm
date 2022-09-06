package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.JoinStatement;
import com.moxa.dream.antlr.smt.Statement;

public class JoinExpr extends SqlExpr {
    private JoinStatement joinStatement;
    private ExprType[] ON = new ExprType[]{ExprType.ON};

    public JoinExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.COMMA, ExprType.LEFT, ExprType.RIGHT, ExprType.CROSS, ExprType.INNER, ExprType.JOIN, ExprType.NIL);
    }

    @Override
    protected Statement exprComma(ExprInfo exprInfo) {
        push();
        joinStatement = new JoinStatement.CommaJoinStatement();
        AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader);
        aliasColumnExpr.setExprTypes(ExprType.HELP);
        joinStatement.setJoinTable(aliasColumnExpr.expr());
        ON = new ExprType[]{ExprType.NIL};
        setExprTypes(ON);
        return expr();
    }

    @Override
    protected Statement exprCross(ExprInfo exprInfo) {
        push();
        joinStatement = new JoinStatement.CrossJoinStatement();
        ON = new ExprType[]{ExprType.ON, ExprType.NIL};
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprLeft(ExprInfo exprInfo) {
        push();
        joinStatement = new JoinStatement.LeftJoinStatement();
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprRight(ExprInfo exprInfo) {
        push();
        joinStatement = new JoinStatement.RightJoinStatement();
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprOuter(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprInner(ExprInfo exprInfo) {
        push();
        joinStatement = new JoinStatement.InnerJoinStatement();
        setExprTypes(ExprType.OUTER, ExprType.JOIN);
        return expr();
    }

    @Override
    protected Statement exprJoin(ExprInfo exprInfo) {
        push();
        AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader);
        aliasColumnExpr.setExprTypes(ExprType.HELP);
        if (joinStatement == null) {
            joinStatement = new JoinStatement.InnerJoinStatement();
        }
        joinStatement.setJoinTable(aliasColumnExpr.expr());
        setExprTypes(ON);
        return expr();
    }

    @Override
    protected Statement exprOn(ExprInfo exprInfo) {
        push();
        CompareExpr operTreeExpr = new CompareExpr(exprReader);
        Statement statement = operTreeExpr.expr();
        joinStatement.setOn(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return joinStatement;
    }
}
