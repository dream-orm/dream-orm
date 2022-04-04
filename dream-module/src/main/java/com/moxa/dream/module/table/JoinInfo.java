package com.moxa.dream.module.table;

import com.moxa.dream.module.annotation.Join;

public class JoinInfo {
    private String table;
    private String column;
    private String joinTable;
    private String joinColumn;
    private Join.JoinType joinType;

    public JoinInfo(String table, String column, String joinTable, String joinColumn, Join.JoinType joinType) {
        this.table = table;
        this.column = column;
        this.joinTable = joinTable;
        this.joinColumn = joinColumn;
        this.joinType = joinType;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public String getJoinColumn() {
        return joinColumn;
    }

    public Join.JoinType getJoinType() {
        return joinType;
    }
}
