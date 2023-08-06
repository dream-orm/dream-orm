package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.*;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainQueryCreatorFactory implements QueryCreatorFactory<ChainQueryDef,ChainSelectDef,ChainFromDef,ChainWhereDef,ChainGroupByDef,ChainHavingDef,ChainOrderByDef,ChainLimitDef,ChainUnionDef,ChainForUpdateDef> {
    private FlexMapper flexMapper;
    public ChainQueryCreatorFactory(FlexMapper flexMapper){
        this.flexMapper=flexMapper;
    }
    @Override
    public ChainQueryDef newQueryDef() {
        return new ChainQueryDef(this,flexMapper);
    }

    @Override
    public ChainSelectDef newSelectDef(QueryStatement statement) {
        return new ChainSelectDef(statement, this,flexMapper);
    }

    @Override
    public ChainFromDef newFromDef(QueryStatement statement) {
        return new ChainFromDef(statement, this,flexMapper);
    }

    @Override
    public ChainWhereDef newWhereDef(QueryStatement statement) {
        return new ChainWhereDef(statement, this,flexMapper);
    }

    @Override
    public ChainGroupByDef newGroupByDef(QueryStatement statement) {
        return new ChainGroupByDef(statement, this,flexMapper);
    }

    @Override
    public ChainHavingDef newHavingDef(QueryStatement statement) {
        return new ChainHavingDef(statement, this,flexMapper);
    }

    @Override
    public ChainOrderByDef newOrderByDef(QueryStatement statement) {
        return new ChainOrderByDef(statement, this,flexMapper);
    }

    @Override
    public ChainLimitDef newLimitDef(QueryStatement statement) {
        return new ChainLimitDef(statement, this,flexMapper);
    }

    @Override
    public ChainUnionDef newUnionDef(QueryStatement statement) {
        return new ChainUnionDef(statement, this,flexMapper);
    }

    @Override
    public ChainForUpdateDef newForUpdateDef(QueryStatement statement) {
        return new ChainForUpdateDef(statement, this,flexMapper);
    }
}
