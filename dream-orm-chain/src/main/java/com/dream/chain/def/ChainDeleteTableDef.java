package com.dream.chain.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainDeleteTableDef extends AbstractChainDeleteDef implements DeleteTableDef<ChainDeleteWhereDef> {
    public ChainDeleteTableDef(FlexDeleteFactory creatorFactory, FlexMapper flexMapper) {
        super(new DeleteStatement(), creatorFactory, flexMapper);
    }
}
