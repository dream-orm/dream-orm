package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.LimitDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultLimitDef extends AbstractQuery implements LimitDef {
    public DefaultLimitDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
