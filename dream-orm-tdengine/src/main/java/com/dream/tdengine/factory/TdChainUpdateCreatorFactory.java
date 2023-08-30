package com.dream.tdengine.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.def.TdChainUpdateColumnDefDef;
import com.dream.tdengine.def.TdChainUpdateDef;
import com.dream.tdengine.def.TdChainUpdateTableDef;

public class TdChainUpdateCreatorFactory implements UpdateCreatorFactory<TdChainUpdateTableDef, TdChainUpdateColumnDefDef, TdChainUpdateDef> {
    private FlexMapper flexMapper;

    public TdChainUpdateCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainUpdateTableDef newUpdateTableDef() {
        return new TdChainUpdateTableDef(this, flexMapper);
    }

    @Override
    public TdChainUpdateColumnDefDef newUpdateColumnDef(UpdateStatement statement) {
        return new TdChainUpdateColumnDefDef(statement, this, flexMapper);
    }

    @Override
    public TdChainUpdateDef newUpdateDef(UpdateStatement statement) {
        return new TdChainUpdateDef(statement, this, flexMapper);
    }
}
