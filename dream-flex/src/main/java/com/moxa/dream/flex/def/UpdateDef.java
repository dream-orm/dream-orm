package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.UpdateStatement;

public class UpdateDef {
    protected UpdateStatement statement = new UpdateStatement();

    UpdateColumnDef update(TableDef tableDef) {
        statement.setTable(tableDef.getStatement());
        return new UpdateColumnDef(statement);
    }
}
