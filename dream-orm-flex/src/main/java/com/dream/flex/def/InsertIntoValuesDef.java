package com.dream.flex.def;

import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.flex.statement.TakeMarkInvokerStatement;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


public interface InsertIntoValuesDef<Insert extends InsertDef> extends InsertDef {
    default Insert values(List<Object> valueList) {
        return values(valueList.toArray(new Object[valueList.size()]));
    }

    default Insert values(Object... values) {
        ListColumnStatement valueListStatement = new ListColumnStatement(",");
        valueListStatement.add(Arrays.stream(values).map(value -> new TakeMarkInvokerStatement(null, value)).toArray(Statement[]::new));
        InsertStatement.ValuesStatement valuesStatement = new InsertStatement.ValuesStatement();
        valuesStatement.setStatement(new BraceStatement(valueListStatement));
        statement().setValues(valuesStatement);
        return (Insert) creatorFactory().newInsertDef(statement());
    }

    default <T> Insert valuesList(List<T> valueList, Function<T, Object[]> fn) {
        ListColumnStatement valueListStatement = new ListColumnStatement(",");
        Statement[] statements = new Statement[valueList.size()];
        for (int i = 0; i < valueList.size(); i++) {
            T value = valueList.get(i);
            Object[] objects = fn.apply(value);
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            listColumnStatement.add(Arrays.stream(objects).map(object -> new TakeMarkInvokerStatement(null, object)).toArray(Statement[]::new));
            statements[i] = new BraceStatement(listColumnStatement);
        }
        valueListStatement.add(statements);
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
