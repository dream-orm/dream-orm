package com.dream.flex.def.defaults;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdate;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateDef extends AbstractUpdate implements UpdateDef {
    public DefaultUpdateDef(UpdateCreatorFactory creatorFactory) {
        super(new UpdateStatement(), creatorFactory);
    }
}
