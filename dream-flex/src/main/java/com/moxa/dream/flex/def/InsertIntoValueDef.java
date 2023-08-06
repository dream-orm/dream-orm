package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;


public class InsertIntoValueDef implements Insert {
    private InsertStatement statement;

    protected InsertIntoValueDef(InsertStatement statement) {
        this.statement = statement;
    }

    public InsertIntoValueDef value(ColumnDef columnDef, Object value) {
        Statement columns = statement.getColumns();
        Statement values = statement.getValues();
        if (columns == null && values == null) {
            ListColumnStatement columnsListStatement = new ListColumnStatement(",");
            ListColumnStatement valuesListStatement = new ListColumnStatement(",");
            columns = new BraceStatement(columnsListStatement);
            values = new BraceStatement(valuesListStatement);
            statement.setColumns(columns);
            statement.setValues(values);
        }
        ((ListColumnStatement) ((BraceStatement) columns).getStatement()).add(columnDef.getStatement());
        ((ListColumnStatement) ((BraceStatement) values).getStatement()).add(new FlexMarkInvokerStatement(value));
        return this;
    }

    @Override
    public InsertStatement getStatement() {
        return statement;
    }
}
