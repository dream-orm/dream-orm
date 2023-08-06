package com.dream.flex.def;

import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.flex.invoker.FlexMarkInvokerStatement;


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
