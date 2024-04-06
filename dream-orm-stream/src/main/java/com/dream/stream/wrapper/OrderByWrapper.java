package com.dream.stream.wrapper;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;
import com.dream.antlr.smt.SymbolStatement;

import java.util.function.Consumer;

public interface OrderByWrapper<T,
        Limit extends LimitWrapper<T, Union, ForUpdate, Query>,
        Union extends UnionWrapper<T, ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<T, Query>,
        Query extends QueryWrapper<T>> extends LimitWrapper<T, Union, ForUpdate, Query> {

    default Limit orderBy(String... columns) {
        ListColumnStatement columnStatement = new ListColumnStatement(",");
        for (String column : columns) {
            columnStatement.add(new SymbolStatement.LetterStatement(column));
        }
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setStatement(columnStatement);
        statement().setOrderStatement(orderStatement);
        return (Limit) creatorFactory().newLimitWrapper(entityType(), statement());
    }

    default Limit orderBy(Consumer<SortWrapper> fn) {
        SortWrapper sortWrapper = new SortWrapper();
        fn.accept(sortWrapper);
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setStatement(sortWrapper.getColumnStatement());
        statement().setOrderStatement(orderStatement);
        return (Limit) creatorFactory().newLimitWrapper(entityType(), statement());
    }
}
