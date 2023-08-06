package com.dream.flex.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateColumnDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.def.UpdateWhereDef;
import com.dream.flex.def.defaults.DefaultUpdateColumnDef;
import com.dream.flex.def.defaults.DefaultUpdateDef;
import com.dream.flex.def.defaults.DefaultUpdateWhereDef;

public class DefaultUpdateCreatorFactory implements UpdateCreatorFactory {
    @Override
    public UpdateDef newUpdateDef() {
        return new DefaultUpdateDef(this);
    }

    @Override
    public UpdateColumnDef newUpdateColumnDef(UpdateStatement statement) {
        return new DefaultUpdateColumnDef(statement, this);
    }

    @Override
    public UpdateWhereDef newUpdateWhereDef(UpdateStatement statement) {
        return new DefaultUpdateWhereDef(statement, this);
    }
}
