package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.UnionDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;

public class DefaultUnionDef extends AbstractQuery implements UnionDef {
    public DefaultUnionDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
