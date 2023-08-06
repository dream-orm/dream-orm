package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;

public abstract class AbstractDelete implements Delete {
    private DeleteStatement statement;
    private DeleteCreatorFactory creatorFactory;

    public AbstractDelete(DeleteStatement statement, DeleteCreatorFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public DeleteStatement statement() {
        return statement;
    }

    @Override
    public DeleteCreatorFactory creatorFactory() {
        return creatorFactory;
    }
}
