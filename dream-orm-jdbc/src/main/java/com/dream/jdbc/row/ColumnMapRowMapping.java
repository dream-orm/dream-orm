package com.dream.jdbc.row;

import com.dream.system.config.Configuration;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.ObjectTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.util.SystemUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ColumnMapRowMapping implements RowMapping<Map<String, Object>> {
    private List<Column> columnList;

    @Override
    public void init(ResultSetMetaData metaData, Configuration configuration) throws SQLException {
        int columnCount = metaData.getColumnCount();
        columnList = new ArrayList<>(columnCount);
        TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
        for (int i = 0; i < columnCount; i++) {
            int jdbcType = metaData.getColumnType(i + 1);
            String columnLabel = metaData.getColumnLabel(i + 1);
            columnLabel = SystemUtil.underlineToCamel(columnLabel);
            TypeHandler typeHandler;
            try {
                typeHandler = typeHandlerFactory.getTypeHandler(Object.class, jdbcType);
            } catch (TypeHandlerNotFoundException e) {
                typeHandler = new ObjectTypeHandler();
            }
            columnList.add(new Column(i + 1, jdbcType, columnLabel, typeHandler));
        }
    }

    @Override
    public Map<String, Object> mapTow(ResultSet resultSet) throws SQLException {
        Map<String, Object> columnMap = new LinkedHashMap<>(columnList.size());
        for (Column column : columnList) {
            columnMap.put(column.columnLabel, column.getValue(resultSet));
        }
        return columnMap;
    }

    class Column {
        int index;
        int jdbcType;
        String columnLabel;
        TypeHandler typeHandler;

        public Column(int index, int jdbcType, String columnLabel, TypeHandler typeHandler) {
            this.index = index;
            this.jdbcType = jdbcType;
            this.columnLabel = columnLabel;
            this.typeHandler = typeHandler;
        }

        public Object getValue(ResultSet resultSet) throws SQLException {
            return typeHandler.getResult(resultSet, index, jdbcType);
        }
    }
}
