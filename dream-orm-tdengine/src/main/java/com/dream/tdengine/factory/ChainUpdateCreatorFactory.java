package com.dream.tdengine.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.tdengine.def.TdChainUpdateColumnDef;
import com.dream.tdengine.def.TdChainUpdateDef;
import com.dream.tdengine.def.TdChainUpdateWhereDef;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainUpdateCreatorFactory implements UpdateCreatorFactory<TdChainUpdateDef, TdChainUpdateColumnDef, TdChainUpdateWhereDef> {
    private FlexMapper flexMapper;

    public ChainUpdateCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainUpdateDef newUpdateDef() {
        return new TdChainUpdateDef(this, flexMapper);
    }

    @Override
    public TdChainUpdateColumnDef newUpdateColumnDef(UpdateStatement statement) {
        return new TdChainUpdateColumnDef(statement, this, flexMapper);
    }

    @Override
    public TdChainUpdateWhereDef newUpdateWhereDef(UpdateStatement statement) {
        return new TdChainUpdateWhereDef(statement, this, flexMapper);
    }
}
