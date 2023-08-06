package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.AbstractInsert;
import com.moxa.dream.flex.def.InsertIntoValuesDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertIntoValuesDef extends AbstractInsert implements InsertIntoValuesDef {
    public DefaultInsertIntoValuesDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
