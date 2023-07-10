package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.GroupStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.QueryStatement;

public class WhereDef extends GroupByDef {

    protected WhereDef(QueryStatement statement) {
        super(statement);
    }

    public GroupByDef groupBy(ColumnDef... columnDefs) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.statement);
        }
        groupStatement.setGroup(listColumnStatement);
        statement.setGroupStatement(groupStatement);
        return new GroupByDef(statement);
    }
}
