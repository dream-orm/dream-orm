package com.dream.flex.def;

import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

import java.util.Arrays;

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
        listColumnStatement.add(Arrays.stream(columnDefs).map(ColumnDef::getStatement).toArray(Statement[]::new));
        groupStatement.setGroup(listColumnStatement);
        statement().setGroupStatement(groupStatement);
        return (Having) creatorFactory().newHavingDef(statement());
    }
}
