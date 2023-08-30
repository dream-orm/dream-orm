package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoValuesDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainInsertIntoValuesDef extends AbstractTdChainInsertDef implements InsertIntoValuesDef<TdChainInsertDef> {
    public TdChainInsertIntoValuesDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }
}
