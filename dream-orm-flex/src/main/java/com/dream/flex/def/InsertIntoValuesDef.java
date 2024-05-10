package com.dream.flex.def;

import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.struct.invoker.TakeMarkInvokerStatement;

import java.util.List;
import java.util.function.Function;


public interface InsertIntoValuesDef<Insert extends InsertDef> extends InsertDef {
    default Insert values(List<Object> valueList) {
        return values(valueList.toArray(new Object[valueList.size()]));
    }

    default Insert values(Object... values) {
        ListColumnStatement valueListStatement = new ListColumnStatement(",");
        for (Object value : values) {
            valueListStatement.add(new TakeMarkInvokerStatement(null, value));
        }
        InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();
        valuesStatement.setStatement(new BraceStatement(valueListStatement));
        statement().setValues(valuesStatement);
        return (Insert) creatorFactory().newInsertDef(statement());
    }

    default <T> Insert valuesList(List<T> valueList, Function<T, Object[]> fn) {
        ListColumnStatement valueListStatement = new ListColumnStatement(",");
        for (T value : valueList) {
            Object[] objects = fn.apply(value);
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            for (Object object : objects) {
                listColumnStatement.add(new TakeMarkInvokerStatement(null, object));
            }
            valueListStatement.add(new BraceStatement(listColumnStatement));
        }
        InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();
        valuesStatement.setStatement(valueListStatement);
        statement().setValues(valuesStatement);
        return (Insert) creatorFactory().newInsertDef(statement());
    }

    default Insert values(QueryDef queryDef) {
        statement().setValues(queryDef.statement());
        return (Insert) creatorFactory().newInsertDef(statement());
    }
}
