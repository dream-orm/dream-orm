package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.AbstractQuery;
import com.moxa.dream.flex.def.ForUpdateDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;

public class DefaultForUpdateDef extends AbstractQuery implements ForUpdateDef {

    public DefaultForUpdateDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory) {
        super(queryStatement, queryCreatorFactory);
    }

}
