package com.dream.chain.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.chain.def.ChainUpdateColumnDef;
import com.dream.chain.def.ChainUpdateDef;
import com.dream.chain.def.ChainUpdateWhereDef;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainUpdateCreatorFactory implements UpdateCreatorFactory<ChainUpdateDef, ChainUpdateColumnDef, ChainUpdateWhereDef> {
    private FlexMapper flexMapper;

    public ChainUpdateCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainUpdateDef newUpdateDef() {
        return new ChainUpdateDef(this, flexMapper);
    }

    @Override
    public ChainUpdateColumnDef newUpdateColumnDef(UpdateStatement statement) {
        return new ChainUpdateColumnDef(statement, this, flexMapper);
    }

    @Override
    public ChainUpdateWhereDef newUpdateWhereDef(UpdateStatement statement) {
        return new ChainUpdateWhereDef(statement, this, flexMapper);
    }
}