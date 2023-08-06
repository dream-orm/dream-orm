package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.flex.def.AbstractInsert;
import com.moxa.dream.flex.def.InsertDef;
import com.moxa.dream.flex.factory.InsertCreatorFactory;

public class DefaultInsertDef extends AbstractInsert implements InsertDef {
    public DefaultInsertDef(InsertCreatorFactory creatorFactory) {
        super(new InsertStatement(), creatorFactory);
    }
}
