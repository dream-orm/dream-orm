package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoValuesDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoValuesDef extends AbstractChainInsertDef implements InsertIntoValuesDef<ChainInsertDef> {
    public ChainInsertIntoValuesDef(InsertStatement insertStatement, FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
