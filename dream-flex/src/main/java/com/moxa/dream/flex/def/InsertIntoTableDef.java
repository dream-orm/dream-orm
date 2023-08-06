package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;


public class InsertIntoTableDef {
    private InsertStatement statement;

    protected InsertIntoTableDef(InsertStatement statement) {
        this.statement = statement;
    }

    public InsertIntoColumnsDef columns(ColumnDef... columnDefs) {
        ListColumnStatement paramsListStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            paramsListStatement.add(columnDef.getStatement());
        }
        statement.setColumns(new BraceStatement(paramsListStatement));
        return new InsertIntoColumnsDef(statement);
    }

    public InsertIntoValueDef value(ColumnDef columnDef, Object value) {
        return new InsertIntoValueDef(statement).value(columnDef, value);
    }
}
