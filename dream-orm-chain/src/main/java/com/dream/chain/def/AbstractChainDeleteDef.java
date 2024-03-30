package com.dream.chain.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDeleteDef;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractChainDeleteDef extends AbstractDeleteDef implements DeleteDef, ChainDelete {
    private FlexMapper flexMapper;

    public AbstractChainDeleteDef(DeleteStatement statement, FlexDeleteFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.delete(this);
    }
}
