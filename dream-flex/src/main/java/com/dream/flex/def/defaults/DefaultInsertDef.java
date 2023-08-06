package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsert;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertDef extends AbstractInsert implements InsertDef {
    public DefaultInsertDef(InsertCreatorFactory creatorFactory) {
        super(new InsertStatement(), creatorFactory);
    }
}
