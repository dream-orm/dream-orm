package com.dream.flex.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.SelectStatement;
import com.dream.antlr.smt.SymbolStatement;

public interface QueryDef<T extends SelectDef> extends Query {

    default T select(ColumnDef... columnDefs) {
        SelectStatement selectStatement = new SelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (columnDefs != null && columnDefs.length > 0) {
            for (ColumnDef columnDef : columnDefs) {
                listColumnStatement.add(columnDef.getStatement());
            }
        } else {
            listColumnStatement.add(new SymbolStatement.LetterStatement("*"));
        }
        selectStatement.setSelectList(listColumnStatement);
        statement().setSelectStatement(selectStatement);
        return (T) creatorFactory().newSelectDef(statement());
    }
}
