package com.dream.flex.factory;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.def.DeleteWhereDef;

public interface DeleteCreatorFactory
        <Delete extends DeleteDef<DeleteTable>,
                DeleteTable extends DeleteTableDef<DeleteWhere>,
                DeleteWhere extends DeleteWhereDef> {

    Delete newDeleteDef();

    DeleteTable newDeleteTableDef(DeleteStatement statement);

    DeleteWhere newDeleteWhereDef(DeleteStatement statement);
}
