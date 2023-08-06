package com.moxa.dream.flex.factory;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.DeleteDef;
import com.moxa.dream.flex.def.DeleteTableDef;
import com.moxa.dream.flex.def.DeleteWhereDef;

public interface DeleteCreatorFactory
        <Delete extends DeleteDef<DeleteTable>,
                DeleteTable extends DeleteTableDef<DeleteWhere>,
                DeleteWhere extends DeleteWhereDef> {

    Delete newDeleteDef();

    DeleteTable newDeleteTableDef(DeleteStatement statement);

    DeleteWhere newDeleteWhereDef(DeleteStatement statement);
}
