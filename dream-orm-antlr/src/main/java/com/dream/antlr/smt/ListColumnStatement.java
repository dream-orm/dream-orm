package com.dream.antlr.smt;

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
            Statement[] tempColumnList = new Statement[columnList.length + 1];
            System.arraycopy(columnList, 0, tempColumnList, 0, columnList.length);
            tempColumnList[columnList.length] = wrapParent(column);
            columnList = tempColumnList;
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
        return isNeedInnerCache(getColumnList());
    }

    @Override
    public ListColumnStatement clone() {
        ListColumnStatement listColumnStatement = (ListColumnStatement) super.clone();
        listColumnStatement.setCut((SymbolStatement.LetterStatement) clone(cut));
        Statement[] columnList = new Statement[this.columnList.length];
        for (int i = 0; i < this.columnList.length; i++) {
            Statement column = this.columnList[i];
            columnList[i] = clone(column);
        }
        listColumnStatement.setColumnList(columnList);
        return listColumnStatement;
    }
}
