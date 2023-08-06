package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.FromDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;

public class DefaultFromDef extends AbstractQuery implements FromDef {
    public DefaultFromDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
