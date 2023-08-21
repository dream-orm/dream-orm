package com.dream.flex.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;

public interface OrderByDef<
        Limit extends LimitDef<Union, ForUpdate, Query>,
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends LimitDef<Union, ForUpdate, Query> {

    default Limit orderBy(SortDef... sortDefs) {
        ListColumnStatement columnStatement = new ListColumnStatement(",");
        for (SortDef sortDef : sortDefs) {
            columnStatement.add(sortDef.getStatement());
        }
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setOrder(columnStatement);
        statement().setOrderStatement(orderStatement);
        return (Limit) creatorFactory().newLimitDef(statement());
    }
}
