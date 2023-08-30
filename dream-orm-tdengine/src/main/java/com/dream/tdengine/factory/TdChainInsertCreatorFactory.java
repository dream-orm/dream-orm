package com.dream.tdengine.factory;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.def.TdChainInsertDef;
import com.dream.tdengine.def.TdChainInsertIntoColumnsDef;
import com.dream.tdengine.def.TdChainInsertIntoTableDef;
import com.dream.tdengine.def.TdChainInsertIntoValuesDef;

public class TdChainInsertCreatorFactory implements InsertCreatorFactory<TdChainInsertIntoTableDef, TdChainInsertIntoColumnsDef, TdChainInsertIntoValuesDef, TdChainInsertDef> {
    private FlexMapper flexMapper;

    public TdChainInsertCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainInsertDef newInsertDef(InsertStatement statement) {
        return new TdChainInsertDef(statement, this, flexMapper);
    }

    @Override
    public TdChainInsertIntoTableDef newInsertIntoTableDef() {
        return new TdChainInsertIntoTableDef(this, flexMapper);
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
