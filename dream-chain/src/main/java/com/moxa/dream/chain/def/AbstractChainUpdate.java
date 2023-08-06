package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.AbstractUpdate;
import com.moxa.dream.flex.def.Update;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public abstract class AbstractChainUpdate extends AbstractUpdate implements Update, ChainUpdate {
    private FlexMapper flexMapper;

    public AbstractChainUpdate(UpdateStatement statement, UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.update(this);
    }
}
