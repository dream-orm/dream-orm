package com.dream.chain.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.chain.def.ChainUpdateColumnDef;
import com.dream.chain.def.ChainUpdateDef;
import com.dream.chain.def.ChainUpdateTableDef;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainFlexUpdateFactory implements FlexUpdateFactory<ChainUpdateTableDef, ChainUpdateColumnDef, ChainUpdateDef> {
    private FlexMapper flexMapper;

    public ChainFlexUpdateFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainUpdateTableDef newUpdateTableDef() {
        return new ChainUpdateTableDef(this, flexMapper);
    }

    @Override
    public ChainUpdateColumnDef newUpdateColumnDef(UpdateStatement statement) {
        return new ChainUpdateColumnDef(statement, this, flexMapper);
    }

    @Override
    public ChainUpdateDef newUpdateDef(UpdateStatement statement) {
        return new ChainUpdateDef(statement, this, flexMapper);
    }
}
