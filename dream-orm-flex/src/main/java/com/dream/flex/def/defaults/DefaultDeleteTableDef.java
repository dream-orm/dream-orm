package com.dream.flex.def.defaults;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDelete;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteTableDef extends AbstractDelete implements DeleteTableDef {
    public DefaultDeleteTableDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
