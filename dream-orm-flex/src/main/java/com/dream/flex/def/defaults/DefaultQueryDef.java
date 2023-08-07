package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.QueryDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultQueryDef extends AbstractQuery implements QueryDef {
    public DefaultQueryDef(QueryCreatorFactory queryCreatorFactory) {
        super(new QueryStatement(), queryCreatorFactory);
    }
}
