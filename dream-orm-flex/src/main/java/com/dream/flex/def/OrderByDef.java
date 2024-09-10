package com.dream.flex.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;
import com.dream.antlr.smt.Statement;

import java.util.Arrays;

public interface OrderByDef<
        Limit extends LimitDef<Union, ForUpdate, Query>,
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends LimitDef<Union, ForUpdate, Query> {

    default Limit orderBy(SortDef... sortDefs) {
        ListColumnStatement columnStatement = new ListColumnStatement(",");
        columnStatement.add(Arrays.stream(sortDefs).map(SortDef::getStatement).toArray(Statement[]::new));
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setStatement(columnStatement);
        statement().setOrderStatement(orderStatement);
        return (Limit) creatorFactory().newLimitDef(statement());
    }
}
