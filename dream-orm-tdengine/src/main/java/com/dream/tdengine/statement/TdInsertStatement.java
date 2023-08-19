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
        this.stdTable = stdTable;
    }

    public ListColumnStatement getTags() {
        return tags;
    }

    public void setTags(ListColumnStatement tags) {
        this.tags = tags;
    }
}
