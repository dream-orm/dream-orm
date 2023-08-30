package com.dream.flex.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.def.DeleteWhereDef;
import com.dream.flex.def.defaults.DefaultDeleteDef;
import com.dream.flex.def.defaults.DefaultDeleteTableDef;
import com.dream.flex.def.defaults.DefaultDeleteWhereDef;

public class DefaultDeleteCreatorFactory implements DeleteCreatorFactory {
    @Override
    public DeleteTableDef newDeleteTableDef() {
        return new DefaultDeleteTableDef(this);
    }

    @Override
    public DeleteWhereDef newDeleteWhereDef(DeleteStatement statement) {
        return new DefaultDeleteWhereDef(statement, this);
    }

    @Override
    public DeleteDef newDeleteDef(DeleteStatement statement) {
        return new DefaultDeleteDef(statement, this);
    }
}
