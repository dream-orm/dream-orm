package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoTableDef extends AbstractChainInsert implements InsertIntoTableDef<ChainInsertIntoColumnsDef> {
    public ChainInsertIntoTableDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
