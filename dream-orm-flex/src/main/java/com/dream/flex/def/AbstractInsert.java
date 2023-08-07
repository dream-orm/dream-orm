package com.dream.flex.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.factory.InsertCreatorFactory;

public abstract class AbstractInsert implements Insert {
    private InsertStatement statement;
    private InsertCreatorFactory creatorFactory;

    public AbstractInsert(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public InsertStatement statement() {
        return statement;
    }

    @Override
    public InsertCreatorFactory creatorFactory() {
        return creatorFactory;
    }
}
