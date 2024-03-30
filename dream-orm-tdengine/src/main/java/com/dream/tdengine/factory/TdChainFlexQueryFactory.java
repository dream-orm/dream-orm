package com.dream.tdengine.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.def.*;

public class TdChainFlexQueryFactory implements FlexQueryFactory<TdChainSelectDef, TdChainFromDef, TdChainWhereDef, TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    private FlexMapper flexMapper;

    public TdChainFlexQueryFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainSelectDef newSelectDef() {
        return new TdChainSelectDef(this, flexMapper);
    }

    @Override
    public TdChainFromDef newFromDef(QueryStatement statement) {
        return new TdChainFromDef(statement, this, flexMapper);
    }

    @Override
    public TdChainWhereDef newWhereDef(QueryStatement statement) {
        return new TdChainWhereDef(statement, this, flexMapper);
    }

    @Override
    public TdChainGroupByDef newGroupByDef(QueryStatement statement) {
        return new TdChainGroupByDef(statement, this, flexMapper);
    }

    @Override
    public TdChainHavingDef newHavingDef(QueryStatement statement) {
        return new TdChainHavingDef(statement, this, flexMapper);
    }

    @Override
    public TdChainOrderByDef newOrderByDef(QueryStatement statement) {
        return new TdChainOrderByDef(statement, this, flexMapper);
    }

    @Override
    public TdChainLimitDef newLimitDef(QueryStatement statement) {
        return new TdChainLimitDef(statement, this, flexMapper);
    }

    @Override
    public TdChainUnionDef newUnionDef(QueryStatement statement) {
        return new TdChainUnionDef(statement, this, flexMapper);
    }

    @Override
    public TdChainForUpdateDef newForUpdateDef(QueryStatement statement) {
        return new TdChainForUpdateDef(statement, this, flexMapper);
    }

    @Override
    public TdChainQueryDef newQueryDef(QueryStatement statement) {
        return new TdChainQueryDef(statement, this, flexMapper);
    }
}
