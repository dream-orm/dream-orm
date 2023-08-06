package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.AbstractDelete;
import com.moxa.dream.flex.def.DeleteDef;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteDef extends AbstractDelete implements DeleteDef {
    public DefaultDeleteDef(DeleteCreatorFactory creatorFactory) {
        super(new DeleteStatement(), creatorFactory);
    }
}
