package com.dream.flex.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.*;
import com.dream.flex.def.defaults.*;

public class DefaultQueryCreatorFactory implements QueryCreatorFactory {
    @Override
    public QueryDef newQueryDef() {
        return new DefaultQueryDef(this);
    }

    @Override
    public SelectDef newSelectDef(QueryStatement statement) {
        return new DefaultSelectDef(statement, this);
    }

    @Override
    public FromDef newFromDef(QueryStatement statement) {
        return new DefaultFromDef(statement, this);
    }

    @Override
    public WhereDef newWhereDef(QueryStatement statement) {
        return new DefaultWhereDef(statement, this);
    }

    @Override
    public GroupByDef newGroupByDef(QueryStatement statement) {
        return new DefaultGroupByDef(statement, this);
    }

    @Override
    public HavingDef newHavingDef(QueryStatement statement) {
        return new DefaultHavingDef(statement, this);
    }

    @Override
    public OrderByDef newOrderByDef(QueryStatement statement) {
        return new DefaultHavingDef(statement, this);
    }

    @Override
    public LimitDef newLimitDef(QueryStatement statement) {
        return new DefaultLimitDef(statement, this);
    }

    @Override
    public UnionDef newUnionDef(QueryStatement statement) {
        return new DefaultUnionDef(statement, this);
    }

    @Override
    public ForUpdateDef newForUpdateDef(QueryStatement statement) {
        return new DefaultForUpdateDef(statement, this);
    }
}
