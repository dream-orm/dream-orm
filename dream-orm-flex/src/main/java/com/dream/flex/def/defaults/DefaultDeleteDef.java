package com.dream.flex.def.defaults;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDeleteDef;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteDef extends AbstractDeleteDef implements DeleteDef {
    public DefaultDeleteDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
