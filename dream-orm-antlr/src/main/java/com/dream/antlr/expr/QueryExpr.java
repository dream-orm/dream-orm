package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.*;

/**
 * 查询语法解析器
 */
public class QueryExpr extends SqlExpr {
    private final QueryStatement queryStatement = new QueryStatement();


    public QueryExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.SELECT);
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        SelectExpr selectExpr = new SelectExpr(exprReader);
        Statement statement = selectExpr.expr();
        queryStatement.setSelectStatement((SelectStatement) statement);
        this.setExprTypes(ExprType.FROM, ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) throws AntlrException {
        FromExpr fromExpr = new FromExpr(exprReader);
        Statement statement = fromExpr.expr();
        queryStatement.setFromStatement((FromStatement) statement);
        setExprTypes(ExprType.WHERE, ExprType.GROUP, ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) throws AntlrException {
        WhereExpr whereExpr = new WhereExpr(exprReader);
        queryStatement.setWhereStatement((WhereStatement) whereExpr.expr());
        setExprTypes(ExprType.GROUP, ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprGroup(ExprInfo exprInfo) throws AntlrException {
        GroupExpr groupExpr = new GroupExpr(exprReader);
        queryStatement.setGroupStatement((GroupStatement) groupExpr.expr());
        setExprTypes(ExprType.HAVING, ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHaving(ExprInfo exprInfo) throws AntlrException {
        HavingExpr havingExpr = new HavingExpr(exprReader);
        queryStatement.setHavingStatement((HavingStatement) havingExpr.expr());
        setExprTypes(ExprType.ORDER, ExprType.LIMIT, ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprOrder(ExprInfo exprInfo) throws AntlrException {
        OrderExpr orderExpr = new OrderExpr(exprReader);
        queryStatement.setOrderStatement((OrderStatement) orderExpr.expr());
        setExprTypes(ExprType.LIMIT, ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprOffSet(ExprInfo exprInfo) throws AntlrException {
        return exprPage(exprInfo);
    }

    @Override
    protected Statement exprLimit(ExprInfo exprInfo) throws AntlrException {
        return exprPage(exprInfo);
    }

    private Statement exprPage(ExprInfo exprInfo) throws AntlrException {
        LimitExpr limitExpr = new LimitExpr(exprReader);
        queryStatement.setLimitStatement((LimitStatement) limitExpr.expr());
        setExprTypes(ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUnion(ExprInfo exprInfo) throws AntlrException {
        UnionExpr unionExpr = new UnionExpr(exprReader);
        UnionStatement unionStatement = (UnionStatement) unionExpr.expr();
        queryStatement.setUnionStatement(unionStatement);
        setExprTypes(ExprType.UNION, ExprType.FOR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFor(ExprInfo exprInfo) throws AntlrException {
        ForUpdateExpr forUpdateExpr = new ForUpdateExpr(exprReader);
        ForUpdateStatement forUpdateStatement = (ForUpdateStatement) forUpdateExpr.expr();
        queryStatement.setForUpdateStatement(forUpdateStatement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return queryStatement;
    }
}
