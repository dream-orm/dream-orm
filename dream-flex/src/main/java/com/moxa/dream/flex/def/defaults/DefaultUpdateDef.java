package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.AbstractUpdate;
import com.moxa.dream.flex.def.UpdateDef;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;

public class DefaultUpdateDef extends AbstractUpdate implements UpdateDef {
    public DefaultUpdateDef(UpdateCreatorFactory creatorFactory) {
        super(new UpdateStatement(), creatorFactory);
    }
}
