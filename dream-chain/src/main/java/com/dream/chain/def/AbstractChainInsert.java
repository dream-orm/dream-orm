package com.dream.chain.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsert;
import com.dream.flex.def.Insert;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractChainInsert extends AbstractInsert implements Insert, ChainInsert {
    private FlexMapper flexMapper;

    public AbstractChainInsert(InsertStatement statement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.insert(this);
    }
}
