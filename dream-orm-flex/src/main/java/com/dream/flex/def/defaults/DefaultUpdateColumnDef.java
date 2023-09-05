package com.dream.flex.def.defaults;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdateDef;
import com.dream.flex.def.UpdateColumnDef;
import com.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateColumnDef extends AbstractUpdateDef implements UpdateColumnDef {
    public DefaultUpdateColumnDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
