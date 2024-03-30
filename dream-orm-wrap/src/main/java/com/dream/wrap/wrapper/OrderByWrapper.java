package com.dream.wrap.wrapper;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;
import com.dream.antlr.smt.SymbolStatement;

public interface OrderByWrapper<
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends LimitWrapper<Union, ForUpdate, Query> {

    default Limit orderBy(String... columns) {
        ListColumnStatement columnStatement = new ListColumnStatement(",");
        for (String column : columns) {
            columnStatement.add(new SymbolStatement.SingleMarkStatement(column));
        }
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setStatement(columnStatement);
        statement().setOrderStatement(orderStatement);
        return (Limit) creatorFactory().newLimitWrapper(statement());
    }
}
