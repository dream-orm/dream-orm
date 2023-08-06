package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.GroupByDef;
import com.moxa.dream.flex.def.QueryCreatorFactory;

public class DefaultGroupByDef extends AbstractQuery implements GroupByDef {
    public DefaultGroupByDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
