package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.OrderStatement;
import com.moxa.dream.antlr.smt.QueryStatement;

public class HavingDef extends OrderByDef {

    protected HavingDef(QueryStatement statement) {
        super(statement);
    }

    public OrderByDef orderBy(SortDef sortDef) {
        OrderStatement orderStatement = new OrderStatement();
        orderStatement.setOrder(sortDef.getStatement());
        statement.setOrderStatement(orderStatement);
        return new OrderByDef(statement);
    }
}
