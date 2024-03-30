package com.dream.tdengine.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.def.TdChainUpdateColumnDef;
import com.dream.tdengine.def.TdChainUpdateDef;
import com.dream.tdengine.def.TdChainUpdateTableDef;

public class TdChainFlexUpdateFactory implements FlexUpdateFactory<TdChainUpdateTableDef, TdChainUpdateColumnDef, TdChainUpdateDef> {
    private FlexMapper flexMapper;

    public TdChainFlexUpdateFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainUpdateTableDef newUpdateTableDef() {
        return new TdChainUpdateTableDef(this, flexMapper);
    }

    @Override
    public TdChainUpdateColumnDef newUpdateColumnDef(UpdateStatement statement) {
        return new TdChainUpdateColumnDef(statement, this, flexMapper);
    }

    @Override
    public TdChainUpdateDef newUpdateDef(UpdateStatement statement) {
        return new TdChainUpdateDef(statement, this, flexMapper);
    }
}
