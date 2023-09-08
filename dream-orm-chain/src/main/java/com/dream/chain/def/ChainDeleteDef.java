package com.dream.chain.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainDeleteDef extends AbstractChainDeleteDef implements DeleteDef {
    public ChainDeleteDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
