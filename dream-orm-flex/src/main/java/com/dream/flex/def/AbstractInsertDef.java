package com.dream.flex.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.factory.InsertCreatorFactory;

public abstract class AbstractInsertDef implements InsertDef {
    private InsertStatement statement;
    private InsertCreatorFactory creatorFactory;

    public AbstractInsertDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
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
