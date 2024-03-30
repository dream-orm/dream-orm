package com.dream.flex.def.defaults;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.AbstractUpdateDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.factory.FlexUpdateFactory;

public class DefaultUpdateDef extends AbstractUpdateDef implements UpdateDef {
    public DefaultUpdateDef(UpdateStatement statement, FlexUpdateFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
