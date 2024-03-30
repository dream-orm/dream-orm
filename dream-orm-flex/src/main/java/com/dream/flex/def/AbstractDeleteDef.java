package com.dream.flex.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.factory.FlexDeleteFactory;

public abstract class AbstractDeleteDef implements DeleteDef {
    private DeleteStatement statement;
    private FlexDeleteFactory creatorFactory;

    public AbstractDeleteDef(DeleteStatement statement, FlexDeleteFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public DeleteStatement statement() {
        return statement;
    }

    @Override
    public FlexDeleteFactory creatorFactory() {
        return creatorFactory;
    }
}
