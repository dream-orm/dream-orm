package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.AbstractDelete;
import com.moxa.dream.flex.def.Delete;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public abstract class AbstractChainDelete extends AbstractDelete implements Delete, ChainDelete {
    private FlexMapper flexMapper;

    public AbstractChainDelete(DeleteStatement statement, DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.delete(this);
    }
}
