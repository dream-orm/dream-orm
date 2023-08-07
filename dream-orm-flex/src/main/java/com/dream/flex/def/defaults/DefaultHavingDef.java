package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.HavingDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultHavingDef extends AbstractQuery implements HavingDef {
    public DefaultHavingDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
