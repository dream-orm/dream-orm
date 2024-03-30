package com.dream.tdengine.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.def.TdChainDeleteDef;
import com.dream.tdengine.def.TdChainDeleteTableDef;
import com.dream.tdengine.def.TdChainDeleteWhereDef;

public class TdChainFlexDeleteFactory implements FlexDeleteFactory<TdChainDeleteTableDef, TdChainDeleteWhereDef, TdChainDeleteDef> {
    private FlexMapper flexMapper;

    public TdChainFlexDeleteFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainDeleteDef newDeleteDef(DeleteStatement statement) {
        return new TdChainDeleteDef(this, flexMapper);
    }

    @Override
    public TdChainDeleteTableDef newDeleteTableDef() {
        return new TdChainDeleteTableDef(this, flexMapper);
    }

    @Override
    public TdChainDeleteWhereDef newDeleteWhereDef(DeleteStatement statement) {
        return new TdChainDeleteWhereDef(statement, this, flexMapper);
    }
}
