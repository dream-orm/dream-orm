package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;

public class QueryExpr extends SqlExpr {
    private final QueryStatement queryStatement = new QueryStatement();


    public QueryExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.SELECT);
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) {
        SelectExpr selectExpr = new SelectExpr(exprReader);
        Statement statement = selectExpr.expr();
        queryStatement.setSelectStatement((SelectStatement) statement);
        this.setExprTypes(ExprType.FROM, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) {
        FromExpr fromExpr = new FromExpr(exprReader);
        Statement statement = fromExpr.expr();
        queryStatement.setFromStatement((FromStatement) statement);
        setExprTypes(ExprType.WHERE, ExprType.GROUP, ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) {
        WhereExpr whereExpr = new WhereExpr(exprReader);
        queryStatement.setWhereStatement((WhereStatement) whereExpr.expr());
        setExprTypes(ExprType.GROUP, ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprGroup(ExprInfo exprInfo) {
        GroupExpr groupExpr = new GroupExpr(exprReader);
        queryStatement.setGroupStatement((GroupStatement) groupExpr.expr());
        setExprTypes(ExprType.HAVING, ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHaving(ExprInfo exprInfo) {
        HavingExpr havingExpr = new HavingExpr(exprReader);
        queryStatement.setHavingStatement((HavingStatement) havingExpr.expr());
        setExprTypes(ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprOrder(ExprInfo exprInfo) {
        OrderExpr orderExpr = new OrderExpr(exprReader);
        queryStatement.setOrderStatement((OrderStatement) orderExpr.expr());
        setExprTypes(ExprType.LIMIT, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprOffSet(ExprInfo exprInfo) {
        return exprPage(exprInfo);
    }

    @Override
    protected Statement exprLimit(ExprInfo exprInfo) {
        return exprPage(exprInfo);
    }

    private Statement exprPage(ExprInfo exprInfo) {
        LimitExpr limitExpr = new LimitExpr(exprReader);
        queryStatement.setLimitStatement((LimitStatement) limitExpr.expr());
        setExprTypes(ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUnion(ExprInfo exprInfo) {
        UnionExpr unionExpr = new UnionExpr(exprReader);
        UnionStatement unionStatement = (UnionStatement) unionExpr.expr();
        queryStatement.setUnionStatement(unionStatement);
        setExprTypes(ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return queryStatement;
    }
}
