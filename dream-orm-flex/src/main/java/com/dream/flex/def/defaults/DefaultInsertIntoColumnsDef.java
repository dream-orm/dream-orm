package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsert;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertIntoColumnsDef extends AbstractInsert implements InsertIntoColumnsDef {
    public DefaultInsertIntoColumnsDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
