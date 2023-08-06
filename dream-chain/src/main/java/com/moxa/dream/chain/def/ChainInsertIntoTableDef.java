package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.InsertIntoTableDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoTableDef extends AbstractChainInsert implements InsertIntoTableDef<ChainInsertIntoColumnsDef> {
    public ChainInsertIntoTableDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
