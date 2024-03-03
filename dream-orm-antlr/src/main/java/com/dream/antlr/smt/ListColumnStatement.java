package com.dream.antlr.smt;

import java.util.ArrayList;
import java.util.List;

public class ListColumnStatement extends Statement {
    private SymbolStatement.LetterStatement cut;
    private List<Statement> columnList = new ArrayList<>(8);

    public ListColumnStatement() {
        this(",");
    }

    public ListColumnStatement(String cut) {
        setCut(new SymbolStatement.LetterStatement(cut));
    }

    public void add(Statement column) {
        if (column != null) {
            columnList.add(wrapParent(column));
        }
    }

    public SymbolStatement.LetterStatement getCut() {
        return cut;
    }

    public void setCut(SymbolStatement.LetterStatement cut) {
        this.cut = wrapParent(cut);
    }

    public Statement[] getColumnList() {
        return columnList.toArray(new Statement[columnList.size()]);
    }

    public void setColumnList(List<Statement> columnList) {
        if (columnList != null) {
            this.columnList = columnList;
            for (Statement statement : columnList) {
                wrapParent(statement);
            }
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(getColumnList());
    }

    @Override
    public ListColumnStatement clone() {
        ListColumnStatement listColumnStatement = (ListColumnStatement) super.clone();
        listColumnStatement.setCut((SymbolStatement.LetterStatement) clone(cut));
        List<Statement> columnList = new ArrayList<>(this.columnList);
        listColumnStatement.setColumnList(columnList);
        return listColumnStatement;
    }
}
