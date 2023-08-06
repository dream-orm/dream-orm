package com.dream.chain.factory;

import com.dream.antlr.smt.InsertStatement;
import com.dream.chain.def.ChainInsertDef;
import com.dream.chain.def.ChainInsertIntoColumnsDef;
import com.dream.chain.def.ChainInsertIntoTableDef;
import com.dream.chain.def.ChainInsertIntoValuesDef;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainInsertCreatorFactory implements InsertCreatorFactory<ChainInsertDef, ChainInsertIntoTableDef, ChainInsertIntoColumnsDef, ChainInsertIntoValuesDef> {
    private FlexMapper flexMapper;

    public ChainInsertCreatorFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainInsertDef newInsertDef() {
        return new ChainInsertDef(this, flexMapper);
    }

    @Override
    public ChainInsertIntoTableDef newInsertIntoTableDef(InsertStatement statement) {
        return new ChainInsertIntoTableDef(statement, this, flexMapper);
    }

    @Override
    public ChainInsertIntoColumnsDef newInsertIntoColumnsDef(InsertStatement statement) {
        return new ChainInsertIntoColumnsDef(statement, this, flexMapper);
    }

    @Override
    public ChainInsertIntoValuesDef newInsertIntoValuesDef(InsertStatement statement) {
        return new ChainInsertIntoValuesDef(statement, this, flexMapper);
    }
}
