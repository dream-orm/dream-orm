package com.dream.tdengine.statement;

import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

public class TdInsertStatement extends InsertStatement {
    private Statement stdTable;
    private ListColumnStatement tags;

    @Override
    public int getNameId() {
        return InsertStatement.class.getSimpleName().hashCode();
    }

    public Statement getStdTable() {
        return stdTable;
    }

    public void setStdTable(Statement stdTable) {
        this.stdTable = wrapParent(stdTable);
    }

    public ListColumnStatement getTags() {
        return tags;
    }

    public void setTags(ListColumnStatement tags) {
        this.tags = wrapParent(tags);
    }

    @Override
    public TdInsertStatement clone() {
        TdInsertStatement tdInsertStatement = (TdInsertStatement) super.clone();
        tdInsertStatement.setStdTable(clone(stdTable));
        tdInsertStatement.setTags((ListColumnStatement) clone(tags));
        return tdInsertStatement;
    }
}
