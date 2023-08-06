package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.AbstractUpdate;
import com.moxa.dream.flex.def.UpdateColumnDef;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateColumnDef extends AbstractUpdate implements UpdateColumnDef {
    public DefaultUpdateColumnDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
