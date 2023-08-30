package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoColumnsDef extends AbstractChainInsertDef implements InsertIntoColumnsDef<ChainInsertIntoValuesDef, ChainInsertDef> {
    public ChainInsertIntoColumnsDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
