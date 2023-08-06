package com.dream.flex.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateColumnDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.def.UpdateWhereDef;

public interface UpdateCreatorFactory
        <Update extends UpdateDef<UpdateColumn>,
                UpdateColumn extends UpdateColumnDef<UpdateColumn, UpdateWhere>,
                UpdateWhere extends UpdateWhereDef> {
    Update newUpdateDef();

    UpdateColumn newUpdateColumnDef(UpdateStatement statement);

    UpdateWhere newUpdateWhereDef(UpdateStatement statement);
}
