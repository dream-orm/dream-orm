package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainDeleteDef extends AbstractTdChainDelete implements DeleteDef<TdChainDeleteTableDef> {
    public TdChainDeleteDef(DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new DeleteStatement(), creatorFactory, flexMapper);
    }
}
