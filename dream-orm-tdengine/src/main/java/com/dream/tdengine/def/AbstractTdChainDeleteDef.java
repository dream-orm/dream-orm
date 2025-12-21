package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDeleteDef;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractTdChainDeleteDef extends AbstractDeleteDef implements DeleteDef, TdChainDelete {
    private FlexMapper flexMapper;

    public AbstractTdChainDeleteDef(DeleteStatement statement, FlexDeleteFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.execute(this);
    }
}
