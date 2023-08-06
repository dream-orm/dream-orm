package com.moxa.dream.flex.def.defaults;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.AbstractDelete;
import com.moxa.dream.flex.def.DeleteWhereDef;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;

public class DefaultDeleteWhereDef extends AbstractDelete implements DeleteWhereDef {
    public DefaultDeleteWhereDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
