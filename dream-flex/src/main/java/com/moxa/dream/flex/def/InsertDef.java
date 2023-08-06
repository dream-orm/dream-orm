package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.InsertStatement;

public class InsertDef {
    protected InsertStatement statement = new InsertStatement();

    InsertIntoTableDef insertInto(TableDef tableDef) {
        statement.setTable(tableDef.getStatement());
        return new InsertIntoTableDef(statement);
    }
}
