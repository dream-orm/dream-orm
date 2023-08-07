package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertDef extends AbstractChainInsert implements InsertDef<ChainInsertIntoTableDef> {
    public ChainInsertDef(InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new InsertStatement(), creatorFactory, flexMapper);
    }
}
