package com.dream.flex.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.factory.UpdateCreatorFactory;

public abstract class AbstractUpdateDef implements UpdateDef {
    private UpdateStatement statement;
    private UpdateCreatorFactory creatorFactory;

    public AbstractUpdateDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public UpdateStatement statement() {
        return statement;
    }

    @Override
    public UpdateCreatorFactory creatorFactory() {
        return creatorFactory;
    }
}
