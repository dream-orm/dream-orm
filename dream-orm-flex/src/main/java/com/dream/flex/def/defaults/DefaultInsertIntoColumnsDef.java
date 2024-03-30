package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsertDef;
import com.dream.flex.def.InsertIntoColumnsDef;
import com.dream.flex.factory.FlexInsertFactory;

public class DefaultInsertIntoColumnsDef extends AbstractInsertDef implements InsertIntoColumnsDef {
    public DefaultInsertIntoColumnsDef(InsertStatement statement, FlexInsertFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
