package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.SelectDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultSelectDef extends AbstractQuery implements SelectDef {
    public DefaultSelectDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }
}
