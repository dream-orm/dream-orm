package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.InsertStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;


public interface InsertIntoColumnsDef<T extends InsertIntoValuesDef> extends Insert {
    default T values(Object... values) {
        ListColumnStatement valueListStatement = new ListColumnStatement(",");
        for (Object value : values) {
            valueListStatement.add(new FlexMarkInvokerStatement(value));
        }
        InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();
        valuesStatement.setStatement(new BraceStatement(valueListStatement));
        statement().setValues(valuesStatement);
        return (T) creatorFactory().newInsertIntoValuesDef(statement());
    }

    default T values(Query query) {
        statement().setValues(query.statement());
        return (T) creatorFactory().newInsertIntoValuesDef(statement());
    }
}
