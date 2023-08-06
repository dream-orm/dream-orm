package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.OrderStatement;

public interface HavingDef<T extends OrderByDef> extends OrderByDef {

    default T orderBy(SortDef sortDef) {
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setOrder(sortDef.getStatement());
        statement().setOrderStatement(orderStatement);
        return (T) queryCreatorFactory().newOrderByDef(statement());
    }
}
