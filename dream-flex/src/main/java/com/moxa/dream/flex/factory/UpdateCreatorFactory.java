package com.moxa.dream.flex.factory;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.UpdateColumnDef;
import com.moxa.dream.flex.def.UpdateDef;
import com.moxa.dream.flex.def.UpdateWhereDef;

public interface UpdateCreatorFactory
        <Update extends UpdateDef<UpdateColumn>,
                UpdateColumn extends UpdateColumnDef<UpdateColumn, UpdateWhere>,
                UpdateWhere extends UpdateWhereDef> {
    Update newUpdateDef();

    UpdateColumn newUpdateColumnDef(UpdateStatement statement);

    UpdateWhere newUpdateWhereDef(UpdateStatement statement);
}
