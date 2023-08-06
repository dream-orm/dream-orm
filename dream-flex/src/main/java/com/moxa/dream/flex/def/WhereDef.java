package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.GroupStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;

public interface WhereDef<T extends GroupByDef> extends GroupByDef {

    default T groupBy(ColumnDef... columnDefs) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.getStatement());
        }
        groupStatement.setGroup(listColumnStatement);
        QueryCreatorFactory queryCreatorFactory = queryCreatorFactory();
        statement().setGroupStatement(groupStatement);
        return (T) queryCreatorFactory.newGroupByDef(statement());
    }
}
