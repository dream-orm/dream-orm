package com.dream.flex.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;

public interface HavingDef<OrderBy extends OrderByDef, Limit extends LimitDef, Union extends UnionDef, ForUpdate extends ForUpdateDef> extends OrderByDef<Limit, Union, ForUpdate> {

    default OrderBy orderBy(SortDef... sortDefs) {
        ListColumnStatement columnStatement = new ListColumnStatement(",");
        for (SortDef sortDef : sortDefs) {
            columnStatement.add(sortDef.getStatement());
        }
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setOrder(columnStatement);
        statement().setOrderStatement(orderStatement);
        return (OrderBy) creatorFactory().newOrderByDef(statement());
    }
}
