package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainFillDef extends TdChainSLimitDef {
    public TdChainFillDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public void fillNone() {

    }

    public void fillValue(Object value) {

    }

    public void fillPrev() {

    }

    public void fillNull() {

    }

    public void fillLinear() {

    }

    public void fillNext() {

    }
}
