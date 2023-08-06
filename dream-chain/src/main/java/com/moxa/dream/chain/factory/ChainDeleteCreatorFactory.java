package com.moxa.dream.chain.factory;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.chain.def.ChainDeleteDef;
import com.moxa.dream.chain.def.ChainDeleteTableDef;
import com.moxa.dream.chain.def.ChainDeleteWhereDef;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainDeleteCreatorFactory implements DeleteCreatorFactory<ChainDeleteDef, ChainDeleteTableDef, ChainDeleteWhereDef> {
    private FlexMapper flexMapper;

    public ChainDeleteCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainDeleteDef newDeleteDef() {
        return new ChainDeleteDef(this, flexMapper);
    }

    @Override
    public ChainDeleteTableDef newDeleteTableDef(DeleteStatement statement) {
        return new ChainDeleteTableDef(statement, this, flexMapper);
    }

    @Override
    public ChainDeleteWhereDef newDeleteWhereDef(DeleteStatement statement) {
        return new ChainDeleteWhereDef(statement, this, flexMapper);
    }
}
