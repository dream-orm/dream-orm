package com.dream.antlr.smt;

public class QueryStatement extends Statement {
    private SelectStatement selectStatement;
    private FromStatement fromStatement;
    private WhereStatement whereStatement;
    private GroupStatement groupStatement;
    private HavingStatement havingStatement;
    private OrderStatement orderStatement;
    private LimitStatement limitStatement;
    private UnionStatement unionStatement;
    private ForUpdateStatement forUpdateStatement;

    public SelectStatement getSelectStatement() {
        return selectStatement;
    }

    public void setSelectStatement(SelectStatement selectStatement) {
        this.selectStatement = wrapParent(selectStatement);
    }

    public FromStatement getFromStatement() {
        return fromStatement;
    }

    public void setFromStatement(FromStatement fromStatement) {
        this.fromStatement = wrapParent(fromStatement);
    }

    public WhereStatement getWhereStatement() {
        return whereStatement;
    }

    public void setWhereStatement(WhereStatement whereStatement) {
        this.whereStatement = wrapParent(whereStatement);
    }

    public GroupStatement getGroupStatement() {
        return groupStatement;
    }

    public void setGroupStatement(GroupStatement groupStatement) {
        this.groupStatement = wrapParent(groupStatement);
    }

    public HavingStatement getHavingStatement() {
        return havingStatement;
    }

    public void setHavingStatement(HavingStatement havingStatement) {
        this.havingStatement = wrapParent(havingStatement);
    }

    public OrderStatement getOrderStatement() {
        return orderStatement;
    }

    public void setOrderStatement(OrderStatement orderStatement) {
        this.orderStatement = wrapParent(orderStatement);
    }

    public LimitStatement getLimitStatement() {
        return limitStatement;
    }

    public void setLimitStatement(LimitStatement limitStatement) {
        this.limitStatement = wrapParent(limitStatement);
    }

    public UnionStatement getUnionStatement() {
        return unionStatement;
    }

    public void setUnionStatement(UnionStatement unionStatement) {
        this.unionStatement = wrapParent(unionStatement);
    }

    public ForUpdateStatement getForUpdateStatement() {
        return forUpdateStatement;
    }

    public void setForUpdateStatement(ForUpdateStatement forUpdateStatement) {
        this.forUpdateStatement = wrapParent(forUpdateStatement);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(selectStatement, fromStatement, whereStatement, groupStatement, havingStatement, orderStatement, limitStatement, unionStatement, forUpdateStatement);
    }
}
