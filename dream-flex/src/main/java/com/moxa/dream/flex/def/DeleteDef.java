package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.DeleteStatement;

public class DeleteDef {
    protected DeleteStatement statement = new DeleteStatement();

    DeleteTableDef delete(TableDef tableDef) {
        statement.setTable(tableDef.getStatement());
        return new DeleteTableDef(statement);
    }
}
