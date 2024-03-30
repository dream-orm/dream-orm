package com.dream.chain.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteWhereDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainDeleteWhereDef extends AbstractChainDeleteDef implements DeleteWhereDef<ChainDeleteDef> {
    public ChainDeleteWhereDef(DeleteStatement statement, FlexDeleteFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
