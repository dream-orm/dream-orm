package com.dream.tdengine.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.def.TdChainUpdateColumnDef;
import com.dream.tdengine.def.TdChainUpdateDef;
import com.dream.tdengine.def.TdChainUpdateWhereDef;

public class TdChainUpdateCreatorFactory implements UpdateCreatorFactory<TdChainUpdateDef, TdChainUpdateColumnDef, TdChainUpdateWhereDef> {
    private FlexMapper flexMapper;

    public TdChainUpdateCreatorFactory(FlexMapper flexMapper) {
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
