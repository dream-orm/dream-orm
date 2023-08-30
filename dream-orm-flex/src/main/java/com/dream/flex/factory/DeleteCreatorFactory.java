package com.dream.flex.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.def.DeleteWhereDef;

public interface DeleteCreatorFactory
        <DeleteTable extends DeleteTableDef<DeleteWhere>,
                DeleteWhere extends DeleteWhereDef,
                Delete extends DeleteDef> {

    DeleteTable newDeleteTableDef();

    DeleteWhere newDeleteWhereDef(DeleteStatement statement);

    Delete newDeleteDef(DeleteStatement statement);
}
