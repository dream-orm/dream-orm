package com.dream.jdbc.row;

import com.dream.system.config.Configuration;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.util.SystemUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanPropertyRowMapping<T> implements RowMapping<T> {
    Constructor<T> constructor;
    private List<Column> columnList;
    private Map<String, Method> methodMap;

    public BeanPropertyRowMapping(Class<T> javaType) {
        this.constructor = constructor(javaType);
        this.methodMap = methodMap(javaType);
    }

    @Override
    public void init(ResultSetMetaData metaData, Configuration configuration) throws SQLException {
        int columnCount = metaData.getColumnCount();
        columnList = new ArrayList<>(columnCount);
        TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
        TableFactory tableFactory = configuration.getTableFactory();
        for (int i = 0; i < columnCount; i++) {
            int jdbcType = metaData.getColumnType(i + 1);
            String columnLabel = metaData.getColumnLabel(i + 1);
            String tableName = metaData.getTableName(i + 1);
            TableInfo tableInfo = tableFactory.getTableInfo(tableName);
            Method method;
            TypeHandler typeHandler = null;
            if (tableInfo != null) {
                ColumnInfo columnInfo = tableInfo.getColumnInfo(columnLabel);
                method = methodMap.get(columnInfo.getName());
                typeHandler = columnInfo.getTypeHandler();
            } else {
                method = methodMap.get(columnLabel);
                if (method == null) {
                    method = methodMap.get(SystemUtil.underlineToCamel(columnLabel));
                    if (method == null) {
                        throw new DreamRunTimeException("属性" + columnLabel + "，没有setter方法");
                    }
                }
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (typeHandler == null) {
                try {
                    typeHandler = typeHandlerFactory.getTypeHandler(parameterTypes[0], jdbcType);
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(e);
                }
            }
            columnList.add(new Column(i + 1, jdbcType, columnLabel, typeHandler, method));
        }
    }

    @Override
    public T mapTow(ResultSet resultSet) throws SQLException {
        T result;
        try {
            result = constructor.newInstance();
            for (Column column : columnList) {
                Object value = column.getValue(resultSet);
                column.method.invoke(result, value);
            }
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
        return result;
    }

    public Constructor<T> constructor(Class javaType) {
        try {
            Constructor constructor = javaType.getConstructor();
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }

    public Map<String, Method> methodMap(Class javaType) {
        Method[] methods = javaType.getMethods();
        Map<String, Method> methodMap = new HashMap<>(methods.length / 2);
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set") && method.getParameterCount() == 1) {
                method.setAccessible(true);
                String property = String.valueOf(Character.toLowerCase(name.charAt(3))).concat(name.substring(4));
                methodMap.put(property, method);
            }
        }
        return methodMap;
    }

    class Column {
        int index;
        int jdbcType;
        String columnLabel;
        TypeHandler typeHandler;
        Method method;

        public Column(int index, int jdbcType, String columnLabel, TypeHandler typeHandler, Method method) {
            this.index = index;
            this.jdbcType = jdbcType;
            this.columnLabel = columnLabel;
            this.typeHandler = typeHandler;
            this.method = method;
        }

        public Object getValue(ResultSet resultSet) throws SQLException {
            return typeHandler.getResult(resultSet, index, jdbcType);
        }
    }
}
