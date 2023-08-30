package com.dream.flex.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.def.UpdateDefColumnDef;
import com.dream.flex.def.UpdateTableDef;

public interface UpdateCreatorFactory
        <UpdateTable extends UpdateTableDef<UpdateColumn>,
                UpdateColumn extends UpdateDefColumnDef<UpdateColumn, Update>,
                Update extends UpdateDef> {
    UpdateTable newUpdateTableDef();

    UpdateColumn newUpdateColumnDef(UpdateStatement statement);

    Update newUpdateDef(UpdateStatement statement);
}
