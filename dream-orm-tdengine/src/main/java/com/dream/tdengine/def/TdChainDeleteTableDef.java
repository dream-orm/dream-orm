package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainDeleteTableDef extends AbstractTdChainDeleteDef implements DeleteTableDef<TdChainDeleteWhereDef> {
    public TdChainDeleteTableDef(FlexDeleteFactory creatorFactory, FlexMapper flexMapper) {
        super(new DeleteStatement(), creatorFactory, flexMapper);
    }
}
