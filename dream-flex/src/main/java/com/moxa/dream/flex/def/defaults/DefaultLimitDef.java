package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.LimitDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;

public class DefaultLimitDef extends AbstractQuery implements LimitDef {
    public DefaultLimitDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
