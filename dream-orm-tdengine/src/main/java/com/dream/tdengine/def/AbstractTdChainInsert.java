package com.dream.tdengine.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsert;
import com.dream.flex.def.Insert;
import com.dream.flex.factory.InsertCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractTdChainInsert extends AbstractInsert implements Insert, TdChainInsert {
    private FlexMapper flexMapper;

    public AbstractTdChainInsert(InsertStatement statement, InsertCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.insert(this);
    }
}
