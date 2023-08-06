package com.moxa.dream.flex.factory;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.UpdateColumnDef;
import com.moxa.dream.flex.def.UpdateDef;
import com.moxa.dream.flex.def.UpdateWhereDef;
import com.moxa.dream.flex.def.defaults.DefaultUpdateColumnDef;
import com.moxa.dream.flex.def.defaults.DefaultUpdateDef;
import com.moxa.dream.flex.def.defaults.DefaultUpdateWhereDef;

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
