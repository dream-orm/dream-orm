package com.moxa.dream.chain.factory;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.chain.def.ChainUpdateColumnDef;
import com.moxa.dream.chain.def.ChainUpdateDef;
import com.moxa.dream.chain.def.ChainUpdateWhereDef;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

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