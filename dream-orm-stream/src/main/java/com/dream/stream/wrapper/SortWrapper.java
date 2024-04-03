package com.dream.stream.wrapper;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.OrderStatement;
import com.dream.antlr.smt.SymbolStatement;

public class SortWrapper {
    private ListColumnStatement columnStatement = new ListColumnStatement(",");

    public SortWrapper asc(String column) {
        columnStatement.add(new OrderStatement.AscStatement(new SymbolStatement.LetterStatement(column)));
        return this;
    }

    public SortWrapper desc(String column) {
        columnStatement.add(new OrderStatement.DescStatement(new SymbolStatement.LetterStatement(column)));
        return this;
    }

    public ListColumnStatement getColumnStatement() {
        return columnStatement;
    }

}
