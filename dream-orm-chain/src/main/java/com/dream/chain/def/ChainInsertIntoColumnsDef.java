package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoColumnsDef extends AbstractChainInsertDef implements InsertIntoColumnsDef<ChainInsertIntoValuesDef, ChainInsertDef> {
    public ChainInsertIntoColumnsDef(InsertStatement insertStatement, FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
