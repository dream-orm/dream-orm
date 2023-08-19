package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdInsertStatement;

public class TdChainInsertDef extends AbstractTdChainInsert implements InsertDef<TdChainInsertIntoTableDef> {
    public TdChainInsertDef(InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new TdInsertStatement(), creatorFactory, flexMapper);
    }
}
