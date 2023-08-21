package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.*;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultQueryDef extends AbstractQueryDef implements SelectDef, FromDef, WhereDef, GroupByDef, HavingDef, OrderByDef, LimitDef, UnionDef, ForUpdateDef, QueryDef {

    public DefaultQueryDef(QueryCreatorFactory creatorFactory) {
        this(new QueryStatement(), creatorFactory);
    }

    public DefaultQueryDef(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
