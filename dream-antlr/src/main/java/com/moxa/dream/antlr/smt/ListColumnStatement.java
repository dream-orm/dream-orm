package com.moxa.dream.antlr.smt;

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
            columnList[columnList.length - 1] = column;
            column.parentStatement = this;
        }
    }

    public SymbolStatement.LetterStatement getCut() {
        return cut;
    }

    public void setCut(SymbolStatement.LetterStatement cut) {
        this.cut = cut;
        if (cut != null) {
            cut.parentStatement = this;
        }
    }

    public Statement[] getColumnList() {
        return columnList;
    }

    public void setColumnList(Statement[] columnList) {
        if (columnList != null) {
            this.columnList = columnList;
            for (Statement statement : columnList) {
                statement.parentStatement = this;
            }
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(columnList);
    }
}
