package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.GroupByDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultGroupByDef extends AbstractQuery implements GroupByDef {
    public DefaultGroupByDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
