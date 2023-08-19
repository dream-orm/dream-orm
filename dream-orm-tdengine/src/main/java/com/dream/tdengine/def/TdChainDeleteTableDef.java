package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainDeleteTableDef extends AbstractTdChainDelete implements DeleteTableDef<TdChainDeleteWhereDef> {
    public TdChainDeleteTableDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
