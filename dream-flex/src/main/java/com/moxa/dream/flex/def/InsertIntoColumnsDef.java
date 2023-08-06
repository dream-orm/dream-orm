package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;


public class InsertIntoColumnsDef {
    private InsertStatement statement;

    protected InsertIntoColumnsDef(InsertStatement statement) {
        this.statement = statement;
    }

    public InsertIntoValuesDef values(Object... values) {
        ListColumnStatement valueListStatement = new ListColumnStatement(",");
        for (Object value : values) {
            valueListStatement.add(new FlexMarkInvokerStatement(value));
        }
        InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();
        valuesStatement.setStatement(new BraceStatement(valueListStatement));
        statement.setValues(valuesStatement);
        return new InsertIntoValuesDef(statement);
    }

    public InsertIntoValuesDef values(Query query) {
        statement.setValues(query.statement());
        return new InsertIntoValuesDef(statement);
    }
}
