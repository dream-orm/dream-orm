package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdate;
import com.dream.flex.def.Update;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractTdChainUpdate extends AbstractUpdate implements Update, TdChainUpdate {
    private FlexMapper flexMapper;

    public AbstractTdChainUpdate(UpdateStatement statement, UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.update(this);
    }
}
