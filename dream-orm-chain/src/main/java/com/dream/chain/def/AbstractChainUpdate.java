package com.dream.chain.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdate;
import com.dream.flex.def.Update;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

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
