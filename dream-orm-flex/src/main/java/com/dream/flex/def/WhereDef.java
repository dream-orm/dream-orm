package com.dream.flex.def;

import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.ListColumnStatement;

public interface WhereDef<GroupBy extends GroupByDef, Having extends HavingDef, OrderBy extends OrderByDef, Limit extends LimitDef, Union extends UnionDef, ForUpdate extends ForUpdateDef> extends GroupByDef<Having, OrderBy, Limit, Union, ForUpdate> {

    default GroupBy groupBy(ColumnDef... columnDefs) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.getStatement());
        }
        groupStatement.setGroup(listColumnStatement);
        statement().setGroupStatement(groupStatement);
        return (GroupBy) creatorFactory().newGroupByDef(statement());
    }
}
