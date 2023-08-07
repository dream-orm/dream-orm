package com.dream.flex.def.defaults;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdate;
import com.dream.flex.def.UpdateWhereDef;
import com.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateWhereDef extends AbstractUpdate implements UpdateWhereDef {
    public DefaultUpdateWhereDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
