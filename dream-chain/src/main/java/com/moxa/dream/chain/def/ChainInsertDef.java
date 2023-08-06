package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.InsertDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainInsertDef extends AbstractChainInsert implements InsertDef<ChainInsertIntoTableDef> {
    public ChainInsertDef(InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new InsertStatement(), creatorFactory, flexMapper);
    }
}
