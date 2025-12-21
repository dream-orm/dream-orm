package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdateDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;

public abstract class AbstractTdChainUpdateDef extends AbstractUpdateDef implements UpdateDef, TdChainUpdate {
    private FlexMapper flexMapper;

    public AbstractTdChainUpdateDef(UpdateStatement statement, FlexUpdateFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public int execute() {
        return flexMapper.execute(this);
    }
}
