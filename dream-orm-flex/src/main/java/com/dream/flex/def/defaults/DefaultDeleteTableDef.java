package com.dream.flex.def.defaults;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDeleteDef;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteTableDef extends AbstractDeleteDef implements DeleteTableDef {
    public DefaultDeleteTableDef(DeleteCreatorFactory creatorFactory) {
        super(new DeleteStatement(), creatorFactory);
    }
}
