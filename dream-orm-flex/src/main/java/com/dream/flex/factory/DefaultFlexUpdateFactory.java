package com.dream.flex.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateColumnDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.def.UpdateTableDef;
import com.dream.flex.def.defaults.DefaultUpdateColumnDef;
import com.dream.flex.def.defaults.DefaultUpdateDef;
import com.dream.flex.def.defaults.DefaultUpdateTable0Def;

public class DefaultFlexUpdateFactory implements FlexUpdateFactory {
    @Override
    public UpdateTableDef newUpdateTableDef() {
        return new DefaultUpdateTable0Def(this);
    }

    @Override
    public UpdateColumnDef newUpdateColumnDef(UpdateStatement statement) {
        return new DefaultUpdateColumnDef(statement, this);
    }

    @Override
    public UpdateDef newUpdateDef(UpdateStatement statement) {
        return new DefaultUpdateDef(statement, this);
    }
}
