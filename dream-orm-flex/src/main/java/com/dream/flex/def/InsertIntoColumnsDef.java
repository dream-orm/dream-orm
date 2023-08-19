package com.dream.flex.def;

import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.flex.invoker.FlexMarkInvokerStatement;

import java.util.List;
import java.util.function.Function;


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

    default T valuesList(List<?> values, Function<Object, Object[]> fn) {
        ListColumnStatement valueListStatement = new ListColumnStatement(",");
        for (Object value : values) {
            Object[] objects = fn.apply(value);
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            for (Object object : objects) {
                listColumnStatement.add(new FlexMarkInvokerStatement(object));
            }
            valueListStatement.add(new BraceStatement(listColumnStatement));
        }
        InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();
        valuesStatement.setStatement(valueListStatement);
        statement().setValues(valuesStatement);
        return (T) creatorFactory().newInsertIntoValuesDef(statement());
    }

    default T values(Query query) {
        statement().setValues(query.statement());
        return (T) creatorFactory().newInsertIntoValuesDef(statement());
    }
}
