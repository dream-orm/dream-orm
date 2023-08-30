package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertIntoTableDef extends AbstractChainInsertDef implements InsertIntoTableDef<ChainInsertIntoColumnsDef> {
    public ChainInsertIntoTableDef(InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new InsertStatement(), creatorFactory, flexMapper);
    }
}
