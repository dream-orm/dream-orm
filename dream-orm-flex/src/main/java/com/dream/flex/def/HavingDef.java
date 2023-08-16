package com.dream.flex.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;

public interface HavingDef<T extends OrderByDef> extends OrderByDef {

    default T orderBy(SortDef... sortDefs) {
        ListColumnStatement columnStatement = new ListColumnStatement(",");
        for (SortDef sortDef : sortDefs) {
            columnStatement.add(sortDef.getStatement());
        }
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setOrder(columnStatement);
        statement().setOrderStatement(orderStatement);
        return (T) creatorFactory().newOrderByDef(statement());
    }
}
