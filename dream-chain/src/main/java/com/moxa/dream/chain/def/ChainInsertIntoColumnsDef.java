package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.InsertIntoColumnsDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoColumnsDef extends AbstractChainInsert implements InsertIntoColumnsDef<ChainInsertIntoValuesDef> {
    public ChainInsertIntoColumnsDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
