package com.dream.system.core.resultsethandler.config;

import com.dream.system.core.resultsethandler.extract.Extractor;
import com.dream.system.table.ColumnInfo;
import com.dream.system.typehandler.handler.TypeHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MappedColumn {
    private int index;
    private int jdbcType;
    private String table;
    private String property;
    private String columnLabel;
    private ColumnInfo columnInfo;
    private TypeHandler typeHandler;
    private Extractor extractor;
    private Field field;

    private MappedColumn() {

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
        if (property == null) {
            property = columnInfo == null ? columnLabel : columnInfo.getName();
        }
        return property;
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

    public Extractor getExtractor() {
        return extractor;
    }

    public boolean isPrimary() {
        return columnInfo == null ? false : columnInfo.isPrimary();
    }

    public Field getField() {
        return field;
    }

    public static class Builder {
        private MappedColumn mappedColumn = new MappedColumn();

        public Builder index(int index) {
            mappedColumn.index = index;
            return this;
        }

        public Builder jdbcType(int jdbcType) {
            mappedColumn.jdbcType = jdbcType;
            return this;
        }

        public Builder table(String table) {
            mappedColumn.table = table;
            return this;
        }

        public Builder columnLabel(String columnLabel) {
            mappedColumn.columnLabel = columnLabel;
            return this;
        }

        public Builder columnInfo(ColumnInfo columnInfo) {
            mappedColumn.columnInfo = columnInfo;
            return this;
        }

        public Builder typeHandler(TypeHandler typeHandler) {
            mappedColumn.typeHandler = typeHandler;
            return this;
        }

        public Builder field(Field field) {
            mappedColumn.field = field;
            return this;
        }

        public Builder property(String property) {
            mappedColumn.property = property;
            return this;
        }

        public Builder extractor(Extractor extractor) {
            mappedColumn.extractor = extractor;
            return this;
        }

        public MappedColumn build() {
            return mappedColumn;
        }

        public int getJdbcType() {
            return mappedColumn.getJdbcType();
        }

        public String getProperty() {
            return mappedColumn.getProperty();
        }

        public String getTable() {
            return mappedColumn.getTable();
        }

        public ColumnInfo getColumnInfo() {
            return mappedColumn.getColumnInfo();
        }
    }
}
