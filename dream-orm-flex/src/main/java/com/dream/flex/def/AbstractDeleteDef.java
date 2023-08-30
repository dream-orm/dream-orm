package com.dream.flex.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.factory.DeleteCreatorFactory;

public abstract class AbstractDeleteDef implements DeleteDef {
    private DeleteStatement statement;
    private DeleteCreatorFactory creatorFactory;

    public AbstractDeleteDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory) {
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
