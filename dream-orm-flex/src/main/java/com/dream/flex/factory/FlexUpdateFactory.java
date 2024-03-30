package com.dream.flex.factory;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateColumnDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.def.UpdateTableDef;

public interface FlexUpdateFactory
        <UpdateTable extends UpdateTableDef<UpdateColumn>,
                UpdateColumn extends UpdateColumnDef<UpdateColumn, Update>,
                Update extends UpdateDef> {
    UpdateTable newUpdateTableDef();

    UpdateColumn newUpdateColumnDef(UpdateStatement statement);

    Update newUpdateDef(UpdateStatement statement);
}
