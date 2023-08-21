package com.dream.flex.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.*;
import com.dream.flex.def.defaults.DefaultQueryDef;

public class DefaultQueryCreatorFactory implements QueryCreatorFactory {

    @Override
    public SelectDef newSelectDef() {
        return new DefaultQueryDef(this);
    }

    @Override
    public FromDef newFromDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public WhereDef newWhereDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public GroupByDef newGroupByDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public HavingDef newHavingDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public OrderByDef newOrderByDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public LimitDef newLimitDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public UnionDef newUnionDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public ForUpdateDef newForUpdateDef(QueryStatement statement) {
        return new DefaultQueryDef(statement, this);
    }

    @Override
    public QueryDef newQueryDef(QueryStatement statement) {
        return new DefaultQueryDef(this);
    }
}
