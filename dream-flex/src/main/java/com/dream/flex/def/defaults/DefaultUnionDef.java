package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.UnionDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultUnionDef extends AbstractQuery implements UnionDef {
    public DefaultUnionDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
