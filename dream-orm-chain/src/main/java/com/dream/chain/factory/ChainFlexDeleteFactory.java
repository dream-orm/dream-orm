package com.dream.chain.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.chain.def.ChainDeleteDef;
import com.dream.chain.def.ChainDeleteTableDef;
import com.dream.chain.def.ChainDeleteWhereDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainFlexDeleteFactory implements FlexDeleteFactory<ChainDeleteTableDef, ChainDeleteWhereDef, ChainDeleteDef> {
    private FlexMapper flexMapper;

    public ChainFlexDeleteFactory(FlexMapper flexMapper) {
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
        return new ChainDeleteDef(statement, this, flexMapper);
    }

}
