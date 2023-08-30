package com.dream.chain.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.chain.def.ChainDeleteDef;
import com.dream.chain.def.ChainDeleteTableDef;
import com.dream.chain.def.ChainDeleteWhereDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainDeleteCreatorFactory implements DeleteCreatorFactory<ChainDeleteTableDef, ChainDeleteWhereDef, ChainDeleteDef> {
    private FlexMapper flexMapper;

    public ChainDeleteCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }


    @Override
    public ChainDeleteTableDef newDeleteTableDef() {
        return new ChainDeleteTableDef(this, flexMapper);
    }

    @Override
    public ChainDeleteWhereDef newDeleteWhereDef(DeleteStatement statement) {
        return new ChainDeleteWhereDef(statement, this, flexMapper);
    }

    @Override
    public ChainDeleteDef newDeleteDef(DeleteStatement statement) {
        return new ChainDeleteDef(this, flexMapper);
    }

}
