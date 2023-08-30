package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainDeleteTableDef extends AbstractTdChainDeleteDef implements DeleteTableDef<TdChainDeleteWhereDef> {
    public TdChainDeleteTableDef(DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new DeleteStatement(), creatorFactory, flexMapper);
    }
}
