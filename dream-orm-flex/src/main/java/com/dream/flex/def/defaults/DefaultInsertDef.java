package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsertDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.factory.FlexInsertFactory;

public class DefaultInsertDef extends AbstractInsertDef implements InsertDef {
    public DefaultInsertDef(InsertStatement statement, FlexInsertFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
