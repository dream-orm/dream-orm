package com.dream.system.table;

public class JoinInfo {
    private final String table;
    private final String column;
    private final String joinTable;
    private final String joinColumn;
    private final JoinType joinType;

    public JoinInfo(String table, String column, String joinTable, String joinColumn, JoinType joinType) {
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

    public JoinType getJoinType() {
        return joinType;
    }
}
