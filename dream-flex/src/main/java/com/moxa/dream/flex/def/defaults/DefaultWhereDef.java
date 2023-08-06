package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.WhereDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;

public class DefaultWhereDef extends AbstractQuery implements WhereDef {
    public DefaultWhereDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
