package com.dream.chain.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.chain.def.ChainDeleteDef;
import com.dream.chain.def.ChainDeleteTableDef;
import com.dream.chain.def.ChainDeleteWhereDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

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
