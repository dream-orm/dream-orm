package com.moxa.dream.module.mapped;

import com.moxa.dream.module.type.handler.TypeHandler;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MappedColumn {
    private int index;
    private int jdbcType;
    private String table;
    private boolean primary;
    private String link;
    private TypeHandler typeHandler;

    public MappedColumn(int index, int jdbcType, String table, boolean primary) {
        this.index = index;
        this.jdbcType = jdbcType;
        this.table = table;
        this.primary = primary;
    }

    public Object getValue(ResultSet resultSet) throws SQLException {
        return typeHandler.getResult(resultSet, index, jdbcType);
    }

    public Object linkObject(ResultSet resultSet, ObjectWrapper target) throws SQLException {
        return target.set(link, getValue(resultSet));
    }

    public String getTable() {
        return table;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        return primary;
    }
}
