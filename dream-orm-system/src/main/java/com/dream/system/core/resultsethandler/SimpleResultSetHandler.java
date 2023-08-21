package com.dream.system.core.resultsethandler;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.util.SystemUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;
import com.dream.util.reflection.factory.ObjectFactory;
import com.dream.util.reflection.wrapper.ObjectFactoryWrapper;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class SimpleResultSetHandler implements ResultSetHandler {
    @Override
    public Object result(ResultSet resultSet, MappedStatement mappedStatement, Session session) throws SQLException {
        TypeHandlerFactory typeHandlerFactory = mappedStatement.getConfiguration().getTypeHandlerFactory();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Column> columnList = new ArrayList<>(columnCount);
        Class<? extends Collection> rowType = mappedStatement.getRowType();
        Class<?> colType = mappedStatement.getColType();
        if (ReflectUtil.isBaseClass(colType)) {
            if (columnCount != 1) {
                throw new DreamRunTimeException("类型错误");
            }
            int columnType = metaData.getColumnType(1);
            try {
                TypeHandler typeHandler = typeHandlerFactory.getTypeHandler(Object.class, columnType);
                columnList.add(new Column(1, typeHandler, null, columnType));
            } catch (TypeHandlerNotFoundException e) {
                throw new DreamRunTimeException(e);
            }
        } else if (Map.class.isAssignableFrom(colType)) {
            for (int i = 0; i < columnCount; i++) {
                int columnType = metaData.getColumnType(i + 1);
                String columnLabel = metaData.getColumnLabel(i + 1);
                columnLabel = SystemUtil.underlineToCamel(columnLabel);
                try {
                    TypeHandler typeHandler = typeHandlerFactory.getTypeHandler(Object.class, columnType);
                    columnList.add(new Column(i + 1, typeHandler, columnLabel, columnType));
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(e);
                }
            }
        } else {
            Method[] methods = colType.getMethods();
            Map<String, Class> fieldMap = new HashMap<>();
            for (Method method : methods) {
                String name = method.getName();
                if (name.startsWith("set") && method.getParameterCount() == 1) {
                    String fieldName = Character.toLowerCase(name.charAt(3)) + name.substring(4);
                    Class<?> parameterType = method.getParameterTypes()[0];
                    fieldMap.put(fieldName, parameterType);
                }
            }
            for (int i = 0; i < columnCount; i++) {
                int columnType = metaData.getColumnType(i + 1);
                String columnLabel = metaData.getColumnLabel(i + 1);
                columnLabel = SystemUtil.underlineToCamel(columnLabel);
                Class type = fieldMap.get(columnLabel);
                if (type != null) {
                    try {
                        TypeHandler typeHandler = typeHandlerFactory.getTypeHandler(type, columnType);
                        columnList.add(new Column(i + 1, typeHandler, columnLabel, columnType));
                    } catch (TypeHandlerNotFoundException e) {
                        throw new DreamRunTimeException(e);
                    }
                }
            }
        }
        ObjectFactoryWrapper rowWrapper = ObjectFactoryWrapper.wrapper(rowType);
        ObjectFactory rowObjectFactory = rowWrapper.newObjectFactory();
        ObjectFactoryWrapper colWrapper = ObjectFactoryWrapper.wrapper(colType);
        while (resultSet.next()) {
            ObjectFactory objectFactory = colWrapper.newObjectFactory();
            for (Column column : columnList) {
                objectFactory.set(column.columnLabel, column.getValue(resultSet));
            }
            rowObjectFactory.set(null, objectFactory.getObject());
        }
        return rowObjectFactory.getObject();
    }

    class Column {
        private int index;
        private TypeHandler typeHandler;
        private String columnLabel;
        private int jdbcType;

        public Column(int index, TypeHandler typeHandler, String columnLabel, int jdbcType) {
            this.index = index;
            this.typeHandler = typeHandler;
            this.columnLabel = columnLabel;
            this.jdbcType = jdbcType;
        }

        public Object getValue(ResultSet resultSet) {
            try {
                return typeHandler.getResult(resultSet, index, jdbcType);
            } catch (SQLException e) {
                throw new DreamRunTimeException(e);
            }
        }
    }
}
