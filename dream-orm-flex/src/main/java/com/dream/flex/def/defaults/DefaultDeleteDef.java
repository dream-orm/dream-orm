package com.dream.flex.def.defaults;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.AbstractDelete;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteDef extends AbstractDelete implements DeleteDef {
    public DefaultDeleteDef(DeleteCreatorFactory creatorFactory) {
        super(new DeleteStatement(), creatorFactory);
    }
}
