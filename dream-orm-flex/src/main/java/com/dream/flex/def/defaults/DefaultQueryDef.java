package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.*;
import com.dream.flex.factory.FlexQueryFactory;

public class DefaultQueryDef extends AbstractQueryDef implements SelectDef, FromDef, WhereDef, GroupByDef, HavingDef, OrderByDef, LimitDef, UnionDef, ForUpdateDef, QueryDef {

    public DefaultQueryDef(FlexQueryFactory creatorFactory) {
        this(new QueryStatement(), creatorFactory);
    }

    public DefaultQueryDef(QueryStatement statement, FlexQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
