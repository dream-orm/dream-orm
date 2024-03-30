package com.dream.flex.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.factory.FlexUpdateFactory;

public abstract class AbstractUpdateDef implements UpdateDef {
    private UpdateStatement statement;
    private FlexUpdateFactory creatorFactory;

    public AbstractUpdateDef(UpdateStatement statement, FlexUpdateFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public UpdateStatement statement() {
        return statement;
    }

    @Override
    public FlexUpdateFactory creatorFactory() {
        return creatorFactory;
    }
}
