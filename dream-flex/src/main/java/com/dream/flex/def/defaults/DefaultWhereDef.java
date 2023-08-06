package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.WhereDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultWhereDef extends AbstractQuery implements WhereDef {
    public DefaultWhereDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
