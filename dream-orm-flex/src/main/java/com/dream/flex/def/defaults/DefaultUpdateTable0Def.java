package com.dream.flex.def.defaults;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdateDef;
import com.dream.flex.def.UpdateTableDef;
import com.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateTable0Def extends AbstractUpdateDef implements UpdateTableDef {
    public DefaultUpdateTable0Def(UpdateCreatorFactory creatorFactory) {
        super(new UpdateStatement(), creatorFactory);
    }
}
