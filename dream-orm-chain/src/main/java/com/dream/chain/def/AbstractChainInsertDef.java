package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsertDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.FlexInsertFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractChainInsertDef extends AbstractInsertDef implements InsertDef, ChainInsert {
    private FlexMapper flexMapper;

    public AbstractChainInsertDef(InsertStatement statement, FlexInsertFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.insert(this);
    }
}
