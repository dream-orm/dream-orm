package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.AbstractDelete;
import com.moxa.dream.flex.def.DeleteTableDef;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteTableDef extends AbstractDelete implements DeleteTableDef {
    public DefaultDeleteTableDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
