package com.dream.flex.def;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.factory.FlexInsertFactory;

public abstract class AbstractInsertDef implements InsertDef {
    private InsertStatement statement;
    private FlexInsertFactory creatorFactory;

    public AbstractInsertDef(InsertStatement statement, FlexInsertFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public InsertStatement statement() {
        return statement;
    }

    @Override
    public FlexInsertFactory creatorFactory() {
        return creatorFactory;
    }
}
