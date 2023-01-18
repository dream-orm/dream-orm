package com.moxa.dream.antlr.smt;

public class QueryStatement extends Statement {
    private SelectStatement selectStatement;
    private FromStatement fromStatement;
    private WhereStatement whereStatement;
    private GroupStatement groupStatement;
    private HavingStatement havingStatement;
    private OrderStatement orderStatement;
    private LimitStatement limitStatement;
    private UnionStatement unionStatement;

    public SelectStatement getSelectStatement() {
        return selectStatement;
    }

    public void setSelectStatement(SelectStatement selectStatement) {
        this.selectStatement = selectStatement;
        if (selectStatement != null) {
            selectStatement.parentStatement = this;
        }
    }

    public FromStatement getFromStatement() {
        return fromStatement;
    }

    public void setFromStatement(FromStatement fromStatement) {
        this.fromStatement = fromStatement;
        if (fromStatement != null) {
            fromStatement.parentStatement = this;
        }
    }

    public WhereStatement getWhereStatement() {
        return whereStatement;
    }

    public void setWhereStatement(WhereStatement whereStatement) {
        this.whereStatement = whereStatement;
        if (whereStatement != null) {
            whereStatement.parentStatement = this;
        }
    }

    public GroupStatement getGroupStatement() {
        return groupStatement;
    }

    public void setGroupStatement(GroupStatement groupStatement) {
        this.groupStatement = groupStatement;
        if (groupStatement != null) {
            groupStatement.parentStatement = this;
        }
    }

    public HavingStatement getHavingStatement() {
        return havingStatement;
    }

    public void setHavingStatement(HavingStatement havingStatement) {
        this.havingStatement = havingStatement;
        if (havingStatement != null) {
            havingStatement.parentStatement = this;
        }
    }

    public OrderStatement getOrderStatement() {
        return orderStatement;
    }

    public void setOrderStatement(OrderStatement orderStatement) {
        this.orderStatement = orderStatement;
        if (orderStatement != null) {
            orderStatement.parentStatement = this;
        }
    }

    public LimitStatement getLimitStatement() {
        return limitStatement;
    }

    public void setLimitStatement(LimitStatement limitStatement) {
        this.limitStatement = limitStatement;
        if (limitStatement != null) {
            limitStatement.parentStatement = this;
        }
    }

    public UnionStatement getUnionStatement() {
        return unionStatement;
    }

    public void setUnionStatement(UnionStatement unionStatement) {
        this.unionStatement = unionStatement;
        if (unionStatement != null) {
            unionStatement.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(selectStatement, fromStatement, whereStatement, groupStatement, havingStatement, orderStatement, limitStatement, unionStatement);
    }
}
