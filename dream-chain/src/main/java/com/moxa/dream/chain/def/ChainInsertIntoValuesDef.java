package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.InsertIntoValuesDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoValuesDef extends AbstractChainInsert implements InsertIntoValuesDef {
    public ChainInsertIntoValuesDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
