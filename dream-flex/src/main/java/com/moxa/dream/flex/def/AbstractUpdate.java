package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;

public abstract class AbstractUpdate implements Update {
    private UpdateStatement statement;
    private UpdateCreatorFactory creatorFactory;

    public AbstractUpdate(UpdateStatement statement, UpdateCreatorFactory creatorFactory) {
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
