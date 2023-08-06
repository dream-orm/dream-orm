package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.AbstractInsert;
import com.moxa.dream.flex.def.Insert;
import com.moxa.dream.flex.factory.InsertCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

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
