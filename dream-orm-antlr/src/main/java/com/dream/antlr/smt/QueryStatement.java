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
        this.selectStatement = selectStatement;
    }

    public FromStatement getFromStatement() {
        return fromStatement;
    }

    public void setFromStatement(FromStatement fromStatement) {
        this.fromStatement = fromStatement;
    }

    public WhereStatement getWhereStatement() {
        return whereStatement;
    }

    public void setWhereStatement(WhereStatement whereStatement) {
        this.whereStatement = whereStatement;
    }

    public GroupStatement getGroupStatement() {
        return groupStatement;
    }

    public void setGroupStatement(GroupStatement groupStatement) {
        this.groupStatement = groupStatement;
    }

    public HavingStatement getHavingStatement() {
        return havingStatement;
    }

    public void setHavingStatement(HavingStatement havingStatement) {
        this.havingStatement = havingStatement;
    }

    public OrderStatement getOrderStatement() {
        return orderStatement;
    }

    public void setOrderStatement(OrderStatement orderStatement) {
        this.orderStatement = orderStatement;
    }

    public LimitStatement getLimitStatement() {
        return limitStatement;
    }

    public void setLimitStatement(LimitStatement limitStatement) {
        this.limitStatement = limitStatement;
    }

    public UnionStatement getUnionStatement() {
        return unionStatement;
    }

    public void setUnionStatement(UnionStatement unionStatement) {
        this.unionStatement = unionStatement;
    }

    public ForUpdateStatement getForUpdateStatement() {
        return forUpdateStatement;
    }

    public void setForUpdateStatement(ForUpdateStatement forUpdateStatement) {
        this.forUpdateStatement = forUpdateStatement;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(selectStatement, fromStatement, whereStatement, groupStatement, havingStatement, orderStatement, limitStatement, unionStatement, forUpdateStatement);
    }

    @Override
    public QueryStatement clone() {
        QueryStatement queryStatement = (QueryStatement) super.clone();
        queryStatement.setSelectStatement((SelectStatement) clone(selectStatement));
        queryStatement.setFromStatement((FromStatement) clone(fromStatement));
        queryStatement.setWhereStatement((WhereStatement) clone(whereStatement));
        queryStatement.setGroupStatement((GroupStatement) clone(groupStatement));
        queryStatement.setHavingStatement((HavingStatement) clone(havingStatement));
        queryStatement.setOrderStatement((OrderStatement) clone(orderStatement));
        queryStatement.setLimitStatement((LimitStatement) clone(limitStatement));
        queryStatement.setUnionStatement((UnionStatement) clone(unionStatement));
        queryStatement.setForUpdateStatement((ForUpdateStatement) clone(forUpdateStatement));
        return queryStatement;
    }
}
