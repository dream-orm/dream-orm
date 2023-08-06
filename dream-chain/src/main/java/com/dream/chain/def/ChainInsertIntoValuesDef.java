package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoValuesDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoValuesDef extends AbstractChainInsert implements InsertIntoValuesDef {
    public ChainInsertIntoValuesDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
