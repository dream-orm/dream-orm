package com.dream.flex.def;

import com.dream.antlr.smt.FromStatement;

public interface FromDef<
        Where extends WhereDef<Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Group extends GroupByDef<Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingDef<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByDef<Limit, Union, ForUpdate, Query>,
        Limit extends LimitDef<Union, ForUpdate, Query>,
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends QueryDef, WhereDef<Group, Having, OrderBy, Limit, Union, ForUpdate, Query> {
    default Where from(TableDef tableDef) {
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(tableDef.getStatement());
        fromStatement.setJoinList(tableDef.getJoinList());
        statement().setFromStatement(fromStatement);
        return (Where) creatorFactory().newFromDef(statement());
    }
}
