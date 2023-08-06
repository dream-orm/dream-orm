package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.AbstractUpdate;
import com.moxa.dream.flex.def.UpdateWhereDef;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateWhereDef extends AbstractUpdate implements UpdateWhereDef {
    public DefaultUpdateWhereDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
