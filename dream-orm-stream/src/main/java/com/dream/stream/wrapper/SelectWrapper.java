package com.dream.stream.wrapper;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

import java.util.Arrays;
import java.util.function.Consumer;

public interface SelectWrapper<T,
        From extends FromWrapper<T, Where, Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Where extends WhereWrapper<T, Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Group extends GroupByWrapper<T, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingWrapper<T, OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<T, Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<T, Union, ForUpdate, Query>,
        Union extends UnionWrapper<T, ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<T, Query>,
        Query extends QueryWrapper<T>> extends FromWrapper<T, Where, Group, Having, OrderBy, Limit, Union, ForUpdate, Query> {

    default From select(String... columns) {
        SelectStatement selectStatement = new SelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (columns != null && columns.length > 0) {
            listColumnStatement.add(Arrays.stream(columns).map(SymbolStatement.LetterStatement::new).toArray(Statement[]::new));
        } else {
            listColumnStatement.add(new SymbolStatement.LetterStatement("*"));
        }
        selectStatement.setSelectList(listColumnStatement);
        statement().setSelectStatement(selectStatement);
        return (From) creatorFactory().newFromWrapper(entityType(), statement());
    }

    default From select(Consumer<FunctionWrapper> fn) {
        FunctionWrapper functionWrapper = new FunctionWrapper();
        fn.accept(functionWrapper);
        ListColumnStatement columnStatement = functionWrapper.getColumnStatement();
        statement().getSelectStatement().setSelectList(columnStatement);
        return (From) creatorFactory().newFromWrapper(entityType(), statement());
    }
}
