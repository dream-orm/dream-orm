package com.dream.chain.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.chain.def.*;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainQueryCreatorFactory implements QueryCreatorFactory<ChainSelectDef, ChainFromDef, ChainWhereDef, ChainGroupByDef, ChainHavingDef, ChainOrderByDef, ChainLimitDef, ChainUnionDef, ChainForUpdateDef, ChainQueryDef> {
    private FlexMapper flexMapper;

    public ChainQueryCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainSelectDef newSelectDef() {
        return new ChainSelectDef(this, flexMapper);
    }

    @Override
    public ChainFromDef newFromDef(QueryStatement statement) {
        return new ChainFromDef(statement, this, flexMapper);
    }

    @Override
    public ChainWhereDef newWhereDef(QueryStatement statement) {
        return new ChainWhereDef(statement, this, flexMapper);
    }

    @Override
    public ChainGroupByDef newGroupByDef(QueryStatement statement) {
        return new ChainGroupByDef(statement, this, flexMapper);
    }

    @Override
    public ChainHavingDef newHavingDef(QueryStatement statement) {
        return new ChainHavingDef(statement, this, flexMapper);
    }

    @Override
    public ChainOrderByDef newOrderByDef(QueryStatement statement) {
        return new ChainOrderByDef(statement, this, flexMapper);
    }

    @Override
    public ChainLimitDef newLimitDef(QueryStatement statement) {
        return new ChainLimitDef(statement, this, flexMapper);
    }

    @Override
    public ChainUnionDef newUnionDef(QueryStatement statement) {
        return new ChainUnionDef(statement, this, flexMapper);
    }

    @Override
    public ChainForUpdateDef newForUpdateDef(QueryStatement statement) {
        return new ChainForUpdateDef(statement, this, flexMapper);
    }

    @Override
    public ChainQueryDef newQueryDef(QueryStatement statement) {
        return new ChainQueryDef(statement, this, flexMapper);
    }
}
