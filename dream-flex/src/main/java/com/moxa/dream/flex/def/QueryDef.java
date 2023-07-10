package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.PreSelectStatement;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.SelectStatement;

public class QueryDef {
    protected QueryStatement statement = new QueryStatement();

    public SelectDef select(ColumnDef... columnDefs) {
        SelectStatement selectStatement = new SelectStatement();
        selectStatement.setPreSelect(new PreSelectStatement());
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.statement);
        }
        selectStatement.setSelectList(listColumnStatement);
        statement.setSelectStatement(selectStatement);
        return new SelectDef(statement);
    }
}
