package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.AbstractInsert;
import com.moxa.dream.flex.def.InsertIntoColumnsDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertIntoColumnsDef extends AbstractInsert implements InsertIntoColumnsDef {
    public DefaultInsertIntoColumnsDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
