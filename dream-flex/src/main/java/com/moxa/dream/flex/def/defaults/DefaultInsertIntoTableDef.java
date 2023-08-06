package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.AbstractInsert;
import com.moxa.dream.flex.def.InsertIntoTableDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertIntoTableDef extends AbstractInsert implements InsertIntoTableDef {
    public DefaultInsertIntoTableDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
