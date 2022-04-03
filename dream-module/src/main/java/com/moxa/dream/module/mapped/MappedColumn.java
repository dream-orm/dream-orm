package com.moxa.dream.module.mapped;

import com.moxa.dream.module.typehandler.handler.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MappedColumn {
    private int index;
    private int jdbcType;
    private String table;
    private boolean primary;
    private TypeHandler typeHandler;
    private String property;

    public MappedColumn(int index, int jdbcType, String table, String property, boolean primary) {
        this.index = index;
        this.jdbcType = jdbcType;
        this.table = table;
        this.property = property;
        this.primary = primary;
    }

    public Object getValue(ResultSet resultSet) throws SQLException {
        return typeHandler.getResult(resultSet, index, jdbcType);
    }

    public String getTable() {
        return table;
    }

    public String getProperty() {
        return property;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public TypeHandler getTypeHandler() {
        return typeHandler;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setTypeHandler(TypeHandler typeHandler) {
        this.typeHandler = typeHandler;
    }

    public boolean isPrimary() {
        return primary;
    }
}
