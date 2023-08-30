package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsertDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertDef extends AbstractInsertDef implements InsertDef {
    public DefaultInsertDef(InsertStatement statement, InsertCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
