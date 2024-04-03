package com.dream.stream.wrapper;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;
import com.dream.antlr.smt.SymbolStatement;

import java.util.function.Consumer;

public interface OrderByWrapper<
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends LimitWrapper<Union, ForUpdate, Query> {

    default Limit orderBy(String... columns) {
        ListColumnStatement columnStatement = new ListColumnStatement(",");
        for (String column : columns) {
            columnStatement.add(new SymbolStatement.LetterStatement(column));
        }
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setStatement(columnStatement);
        statement().setOrderStatement(orderStatement);
        return (Limit) creatorFactory().newLimitWrapper(statement());
    }

    default Limit orderBy(Consumer<SortWrapper>fn) {
        SortWrapper sortWrapper = new SortWrapper();
        fn.accept(sortWrapper);
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setStatement(sortWrapper.getColumnStatement());
        statement().setOrderStatement(orderStatement);
        return (Limit) creatorFactory().newLimitWrapper(statement());
    }
}
