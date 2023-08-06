package com.moxa.dream.flex.factory;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.DeleteDef;
import com.moxa.dream.flex.def.DeleteTableDef;
import com.moxa.dream.flex.def.DeleteWhereDef;
import com.moxa.dream.flex.def.defaults.DefaultDeleteDef;
import com.moxa.dream.flex.def.defaults.DefaultDeleteTableDef;
import com.moxa.dream.flex.def.defaults.DefaultDeleteWhereDef;

public class DefaultDeleteCreatorFactory implements DeleteCreatorFactory {
    @Override
    public DeleteDef newDeleteDef() {
        return new DefaultDeleteDef(this);
    }

    @Override
    public DeleteTableDef newDeleteTableDef(DeleteStatement statement) {
        return new DefaultDeleteTableDef(statement, this);
    }

    @Override
    public DeleteWhereDef newDeleteWhereDef(DeleteStatement statement) {
        return new DefaultDeleteWhereDef(statement, this);
    }
}
