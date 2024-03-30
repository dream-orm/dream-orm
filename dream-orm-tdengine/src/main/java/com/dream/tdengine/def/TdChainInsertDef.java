package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainInsertDef extends AbstractTdChainInsertDef implements InsertDef {
    public TdChainInsertDef(InsertStatement statement, FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
