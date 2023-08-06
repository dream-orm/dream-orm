package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.QueryCreatorFactory;
import com.moxa.dream.flex.def.QueryDef;

public class DefaultQueryDef extends AbstractQuery implements QueryDef {
    public DefaultQueryDef(QueryCreatorFactory queryCreatorFactory) {
        super(new QueryStatement(), queryCreatorFactory);
    }
}
