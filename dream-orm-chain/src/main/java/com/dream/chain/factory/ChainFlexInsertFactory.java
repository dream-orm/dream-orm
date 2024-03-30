package com.dream.chain.factory;

import com.dream.antlr.smt.InsertStatement;
import com.dream.chain.def.ChainInsertDef;
import com.dream.chain.def.ChainInsertIntoColumnsDef;
import com.dream.chain.def.ChainInsertIntoTableDef;
import com.dream.chain.def.ChainInsertIntoValuesDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainFlexInsertFactory implements FlexInsertFactory<ChainInsertIntoTableDef, ChainInsertIntoColumnsDef, ChainInsertIntoValuesDef, ChainInsertDef> {
    private FlexMapper flexMapper;

    public ChainFlexInsertFactory(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }


    @Override
    public ChainInsertIntoTableDef newInsertIntoTableDef() {
        return new ChainInsertIntoTableDef(this, flexMapper);
    }

    @Override
    public ChainInsertIntoColumnsDef newInsertIntoColumnsDef(InsertStatement statement) {
        return new ChainInsertIntoColumnsDef(statement, this, flexMapper);
    }

    @Override
    public ChainInsertIntoValuesDef newInsertIntoValuesDef(InsertStatement statement) {
        return new ChainInsertIntoValuesDef(statement, this, flexMapper);
    }

    @Override
    public ChainInsertDef newInsertDef(InsertStatement statement) {
        return new ChainInsertDef(statement, this, flexMapper);
    }
}
