package com.dream.antlr.smt;

import java.util.Arrays;

public class ListColumnStatement extends Statement {
    private SymbolStatement.LetterStatement cut;
    private Statement[] columnList = new Statement[0];

    public ListColumnStatement() {
        this(",");
    }

    public ListColumnStatement(String cut) {
        setCut(new SymbolStatement.LetterStatement(cut));
    }

    public void add(Statement column) {
        if (column != null) {
            columnList = Arrays.copyOf(columnList, columnList.length + 1);
            columnList[columnList.length - 1] = wrapParent(column);
        }
    }

    public SymbolStatement.LetterStatement getCut() {
        return cut;
    }

    public void setCut(SymbolStatement.LetterStatement cut) {
        this.cut = wrapParent(cut);
    }

    public Statement[] getColumnList() {
        return columnList;
    }

    public void setColumnList(Statement[] columnList) {
        if (columnList != null) {
            this.columnList = columnList;
            for (Statement statement : columnList) {
                wrapParent(statement);
            }
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(columnList);
    }

    @Override
    public ListColumnStatement clone() {
        ListColumnStatement listColumnStatement = (ListColumnStatement) super.clone();
        listColumnStatement.cut = (SymbolStatement.LetterStatement) clone(cut);
        listColumnStatement.columnList = new Statement[columnList.length];
        for (int i = 0; i < columnList.length; i++) {
            listColumnStatement.columnList[i] = clone(columnList[i]);
        }
        return listColumnStatement;
    }
}
