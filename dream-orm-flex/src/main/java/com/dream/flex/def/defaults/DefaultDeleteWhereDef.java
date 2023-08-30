package com.dream.flex.def.defaults;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDeleteDef;
import com.dream.flex.def.DeleteWhereDef;
import com.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteWhereDef extends AbstractDeleteDef implements DeleteWhereDef {
    public DefaultDeleteWhereDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
