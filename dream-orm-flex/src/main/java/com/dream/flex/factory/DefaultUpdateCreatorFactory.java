package com.dream.flex.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.def.UpdateDefColumnDef;
import com.dream.flex.def.UpdateTableDef;
import com.dream.flex.def.defaults.DefaultUpdateColumnDefDef;
import com.dream.flex.def.defaults.DefaultUpdateDef;
import com.dream.flex.def.defaults.DefaultUpdateTable0Def;

public class DefaultUpdateCreatorFactory implements UpdateCreatorFactory {
    @Override
    public UpdateTableDef newUpdateTableDef() {
        return new DefaultUpdateTable0Def(this);
    }

    @Override
    public UpdateDefColumnDef newUpdateColumnDef(UpdateStatement statement) {
        return new DefaultUpdateColumnDefDef(statement, this);
    }

    @Override
    public UpdateDef newUpdateDef(UpdateStatement statement) {
        return new DefaultUpdateDef(statement, this);
    }
}
