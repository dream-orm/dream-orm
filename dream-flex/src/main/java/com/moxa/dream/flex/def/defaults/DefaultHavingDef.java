package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.HavingDef;
import com.moxa.dream.flex.def.QueryCreatorFactory;

public class DefaultHavingDef extends AbstractQuery implements HavingDef {
    public DefaultHavingDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
