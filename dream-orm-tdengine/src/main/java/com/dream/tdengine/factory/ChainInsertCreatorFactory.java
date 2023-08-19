package com.dream.tdengine.factory;

import com.dream.antlr.smt.InsertStatement;
import com.dream.tdengine.def.TdChainInsertDef;
import com.dream.tdengine.def.TdChainInsertIntoColumnsDef;
import com.dream.tdengine.def.TdChainInsertIntoTableDef;
import com.dream.tdengine.def.TdChainInsertIntoValuesDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertCreatorFactory implements InsertCreatorFactory<TdChainInsertDef, TdChainInsertIntoTableDef, TdChainInsertIntoColumnsDef, TdChainInsertIntoValuesDef> {
    private FlexMapper flexMapper;

    public ChainInsertCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainInsertDef newInsertDef() {
        return new TdChainInsertDef(this, flexMapper);
    }

    @Override
    public TdChainInsertIntoTableDef newInsertIntoTableDef(InsertStatement statement) {
        return new TdChainInsertIntoTableDef(statement, this, flexMapper);
    }

    @Override
    public TdChainInsertIntoColumnsDef newInsertIntoColumnsDef(InsertStatement statement) {
        return new TdChainInsertIntoColumnsDef(statement, this, flexMapper);
    }

    @Override
    public TdChainInsertIntoValuesDef newInsertIntoValuesDef(InsertStatement statement) {
        return new TdChainInsertIntoValuesDef(statement, this, flexMapper);
    }
}
