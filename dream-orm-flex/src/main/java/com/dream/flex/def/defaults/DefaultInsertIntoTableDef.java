package com.dream.flex.def.defaults;

import com.dream.antlr.smt.InsertStatement;
import com.dream.flex.def.AbstractInsertDef;
import com.dream.flex.def.InsertIntoTableDef;
import com.dream.flex.factory.FlexInsertFactory;

public class DefaultInsertIntoTableDef extends AbstractInsertDef implements InsertIntoTableDef {
    public DefaultInsertIntoTableDef(FlexInsertFactory creatorFactory) {
        super(new InsertStatement(), creatorFactory);
    }
}
