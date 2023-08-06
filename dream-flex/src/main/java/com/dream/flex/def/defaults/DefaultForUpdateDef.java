package com.dream.flex.def.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.AbstractQuery;
import com.dream.flex.def.ForUpdateDef;
import com.dream.flex.factory.QueryCreatorFactory;

public class DefaultForUpdateDef extends AbstractQuery implements ForUpdateDef {

    public DefaultForUpdateDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }

}
