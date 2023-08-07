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
