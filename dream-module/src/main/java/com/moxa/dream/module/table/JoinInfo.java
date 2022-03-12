package com.moxa.dream.module.table;

import com.moxa.dream.module.annotation.Join;

import java.lang.reflect.Field;

public class JoinInfo {
    private String table;
    private String column;
    private Field field;
    private String joinTable;
    private String joinColumn;
    private Join.JoinType joinType;

    public JoinInfo(String table, String column, Field field, String joinTable, String joinColumn, Join.JoinType joinType) {
        this.table = table;
        this.column = column;
        this.field = field;
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

    public Field getField() {
        return field;
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
