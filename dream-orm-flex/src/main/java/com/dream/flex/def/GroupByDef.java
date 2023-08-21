package com.dream.flex.def;

import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.ListColumnStatement;

public interface GroupByDef<
        Having extends HavingDef<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByDef<Limit, Union, ForUpdate, Query>,
        Limit extends LimitDef<Union, ForUpdate, Query>,
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends HavingDef<OrderBy, Limit, Union, ForUpdate, Query> {

    default Having groupBy(ColumnDef... columnDefs) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.getStatement());
        }
        groupStatement.setGroup(listColumnStatement);
        statement().setGroupStatement(groupStatement);
        return (Having) creatorFactory().newHavingDef(statement());
    }
}
