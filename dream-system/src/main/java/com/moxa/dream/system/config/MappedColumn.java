package com.moxa.dream.system.config;

import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.typehandler.handler.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MappedColumn {
    private final int index;
    private final int jdbcType;
    private final String table;
    private TypeHandler typeHandler;
    private String property;
    private String columnLabel;
    private ColumnInfo columnInfo;

    public MappedColumn(int index, int jdbcType, String table, String columnLabel, ColumnInfo columnInfo) {
        this.index = index;
        this.jdbcType = jdbcType;
        this.table = table;
        this.columnLabel = columnLabel;
        this.columnInfo = columnInfo;
        this.property = columnInfo == null ? columnLabel : columnInfo.getName();
    }

    public Object getValue(ResultSet resultSet) throws SQLException {
        return typeHandler.getResult(resultSet, index, jdbcType);
    }

    public String getTable() {
        return table;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public TypeHandler getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(TypeHandler typeHandler) {
        this.typeHandler = typeHandler;
    }

    public boolean isPrimary() {
        return columnInfo == null ? false : columnInfo.isPrimary();
    }
}
