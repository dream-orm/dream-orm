package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsert;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertIntoTableDef extends AbstractInsert implements InsertIntoTableDef {
    public DefaultInsertIntoTableDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
