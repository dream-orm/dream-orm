package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsertDef;
import com.dream.flex.def.InsertIntoValuesDef;
import com.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertIntoValuesDef extends AbstractInsertDef implements InsertIntoValuesDef {
    public DefaultInsertIntoValuesDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
