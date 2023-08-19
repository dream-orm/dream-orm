package com.dream.tdengine.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.tdengine.def.TdChainDeleteDef;
import com.dream.tdengine.def.TdChainDeleteTableDef;
import com.dream.tdengine.def.TdChainDeleteWhereDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainDeleteCreatorFactory implements DeleteCreatorFactory<TdChainDeleteDef, TdChainDeleteTableDef, TdChainDeleteWhereDef> {
    private FlexMapper flexMapper;

    public ChainDeleteCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainDeleteDef newDeleteDef() {
        return new TdChainDeleteDef(this, flexMapper);
    }

    @Override
    public TdChainDeleteTableDef newDeleteTableDef(DeleteStatement statement) {
        return new TdChainDeleteTableDef(statement, this, flexMapper);
    }

    @Override
    public TdChainDeleteWhereDef newDeleteWhereDef(DeleteStatement statement) {
        return new TdChainDeleteWhereDef(statement, this, flexMapper);
    }
}
