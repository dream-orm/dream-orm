package com.dream.flex.def.defaults;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdateDef;
import com.dream.flex.def.UpdateDefColumnDef;
import com.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateColumnDefDef extends AbstractUpdateDef implements UpdateDefColumnDef {
    public DefaultUpdateColumnDefDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
