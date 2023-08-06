package com.dream.chain.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDelete;
import com.dream.flex.def.Delete;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

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
