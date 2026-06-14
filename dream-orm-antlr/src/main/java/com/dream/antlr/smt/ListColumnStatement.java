package com.dream.antlr.smt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListColumnStatement extends Statement {
    private SymbolStatement.LetterStatement cut;
    private List<Statement> columnList = new ArrayList<>(4);

    public ListColumnStatement() {
        this(",");
    }

    public ListColumnStatement(String cut) {
        setCut(new SymbolStatement.LetterStatement(cut));
    }

    public void add(Statement... columns) {
        if (columns != null && columns.length > 0) {
            columnList.addAll(Arrays.asList(columns));
        }
    }

    public SymbolStatement.LetterStatement getCut() {
        return cut;
    }

    public void setCut(SymbolStatement.LetterStatement cut) {
        this.cut = cut;
    }

    public Statement[] getColumnList() {
        return columnList.toArray(new Statement[0]);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(getColumnList());
    }

    @Override
    public ListColumnStatement clone() {
        ListColumnStatement listColumnStatement = (ListColumnStatement) super.clone();
        listColumnStatement.setCut((SymbolStatement.LetterStatement) clone(cut));
        List<Statement> columnList = new ArrayList<>(this.columnList.size());
        for (Statement column : this.columnList) {
            columnList.add(clone(column));
        }
        listColumnStatement.columnList = columnList;
        return listColumnStatement;
    }
}
