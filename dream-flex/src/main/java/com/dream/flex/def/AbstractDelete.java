package com.dream.flex.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.factory.DeleteCreatorFactory;

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
