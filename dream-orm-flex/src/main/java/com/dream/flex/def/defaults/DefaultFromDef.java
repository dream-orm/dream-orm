package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.FromDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultFromDef extends AbstractQuery implements FromDef {
    public DefaultFromDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
