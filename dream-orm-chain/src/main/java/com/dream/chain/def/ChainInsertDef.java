package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertDef extends AbstractChainInsertDef implements InsertDef {
    public ChainInsertDef(InsertStatement statement, FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
