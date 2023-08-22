package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainInsertIntoColumnsDef extends AbstractTdChainInsert implements InsertIntoColumnsDef<TdChainInsertIntoValuesDef> {
    public TdChainInsertIntoColumnsDef(InsertStatement insertStatement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(insertStatement, creatorFactory, flexMapper);
    }

    @Override
    public TdChainInsertIntoValuesDef file(String file) {
        return super.file(file);
    }
}
