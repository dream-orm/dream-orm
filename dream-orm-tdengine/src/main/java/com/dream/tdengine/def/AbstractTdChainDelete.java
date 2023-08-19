package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDelete;
import com.dream.flex.def.Delete;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractTdChainDelete extends AbstractDelete implements Delete, TdChainDelete {
    private FlexMapper flexMapper;

    public AbstractTdChainDelete(DeleteStatement statement, DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.delete(this);
    }
}
