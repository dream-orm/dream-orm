package com.moxa.dream.system.core.resultsethandler;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedColumn;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.system.typehandler.TypeHandlerNotFoundException;
import com.moxa.dream.system.util.SystemUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectHandler;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class SimpleResultSetHandler implements ResultSetHandler {
    @Override
    public Object result(ResultSet resultSet, MappedStatement mappedStatement, Session session) throws SQLException {
        Class<?> colType = mappedStatement.getColType();
        List<Object> resultList = new ArrayList<>();
        MappedColumnWrapper[] mappedColumnList;
        try {
            mappedColumnList = createMappedColumnWrapper(resultSet, mappedStatement);
        } catch (TypeHandlerNotFoundException e) {
            throw new DreamRunTimeException(mappedStatement.getId() + "获取类型转换器失败，" + e.getMessage(), e);
        }
        if (ReflectUtil.isBaseClass(colType)) {
            if (mappedColumnList.length != 1) {
                throw new DreamRunTimeException("返回类型'" + colType.getName() + "'不支持查询多列");
            }
            while (resultSet.next()) {
                Object result = mappedColumnList[0].getValue(resultSet);
                resultList.add(result);
            }
        } else if (Map.class.isAssignableFrom(colType)) {
            while (resultSet.next()) {
                Map<String, Object> resultMap = new HashMap<>();
                for (MappedColumnWrapper mappedColumnWrapper : mappedColumnList) {
                    Object value = mappedColumnWrapper.getValue(resultSet);
                    resultMap.put(mappedColumnWrapper.getProperty(), value);
                }
                resultList.add(resultMap);
            }
        } else {
            while (resultSet.next()) {
                Object result = ReflectUtil.create(colType);
                for (MappedColumnWrapper mappedColumnWrapper : mappedColumnList) {
                    Object value = mappedColumnWrapper.getValue(resultSet);
                    Method writeMethod = mappedColumnWrapper.getWriteMethod();
                    try {
                        writeMethod.invoke(result, value);
                    } catch (Exception e) {
                        throw new DreamRunTimeException(e);
                    }
                }
                resultList.add(result);
            }
        }
        return resultList;
    }

    private MappedColumnWrapper[] createMappedColumnWrapper(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException, TypeHandlerNotFoundException {
        Configuration configuration = mappedStatement.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        MappedColumn.Builder[] builderList = new MappedColumn.Builder[columnCount];
        MappedColumnWrapper[] mappedColumnWrapperList = new MappedColumnWrapper[columnCount];
        Class colType = mappedStatement.getColType();
        if (colType == Object.class && columnCount > 1)
            colType = HashMap.class;
        Map<String, Method> writeMethod = null;
        for (int i = 1; i <= columnCount; i++) {
            int jdbcType = metaData.getColumnType(i);
            String columnLabel = metaData.getColumnLabel(i);
            String tableName = metaData.getTableName(i);
            MappedColumn.Builder builder = builderList[i - 1] = new MappedColumn
                    .Builder()
                    .index(i)
                    .jdbcType(jdbcType);
            if (ReflectUtil.isBaseClass(colType)) {
                builder.typeHandler(mappedStatement
                        .getConfiguration()
                        .getTypeHandlerFactory()
                        .getTypeHandler(colType
                                , builder.getJdbcType()));
                mappedColumnWrapperList[i - 1] = new MappedColumnWrapper(builder.build());
            } else {
                String fieldName = null;
                if (tableFactory != null) {
                    TableInfo tableInfo = tableFactory.getTableInfo(tableName);
                    if (tableInfo != null) {
                        fieldName = tableInfo.getFieldName(columnLabel);
                    }
                }
                if (fieldName == null) {
                    fieldName = SystemUtil.underlineToCamel(columnLabel);
                }
                if (Map.class.isAssignableFrom(colType)) {
                    builder.property(fieldName).typeHandler(mappedStatement
                            .getConfiguration()
                            .getTypeHandlerFactory()
                            .getTypeHandler(Object.class
                                    , builder.getJdbcType()));
                    mappedColumnWrapperList[i - 1] = new MappedColumnWrapper(builder.build());
                } else {
                    if (writeMethod == null) {
                        writeMethod = getWriteMethod(colType);
                    }
                    Method method = writeMethod.get(fieldName);
                    if (method == null) {
                        throw new DreamRunTimeException("映射失败，映射字段:" + fieldName + "，没有setter方法");
                    }
                    builder.property(fieldName).typeHandler(mappedStatement
                            .getConfiguration()
                            .getTypeHandlerFactory()
                            .getTypeHandler(method.getParameters()[0].getType()
                                    , builder.getJdbcType()));
                    mappedColumnWrapperList[i - 1] = new MappedColumnWrapper(builder.build(), method);
                }
            }
        }
        return mappedColumnWrapperList;
    }

    protected Map<String, Method> getWriteMethod(Class<?> colType) {
        Map<String, Method> writeMethod = new HashMap<>();
        List<Method> methodList = ReflectUtil.find(colType, new ReflectHandler<Method>() {
            @Override
            public List<Method> doHandler(Class type) {
                return Arrays.asList(type.getMethods());
            }

            @Override
            public List<Class> goHandler(Class type) {
                List<Class> list = new ArrayList();
                Class superclass = type.getSuperclass();
                if (superclass != null && superclass != Object.class)
                    list.add(superclass);
                return list;
            }
        });
        for (Method method : methodList) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && method.getParameters().length == 1) {
                if (methodName.length() > 3) {
                    String property = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                    writeMethod.put(property, method);
                }
            }
        }
        return writeMethod;
    }

    public class MappedColumnWrapper {
        private MappedColumn mappedColumn;
        private Method writeMethod;

        public MappedColumnWrapper(MappedColumn mappedColumn) {
            this(mappedColumn, null);
        }

        public MappedColumnWrapper(MappedColumn mappedColumn, Method writeMethod) {
            this.mappedColumn = mappedColumn;
            this.writeMethod = writeMethod;
        }

        public Object getValue(ResultSet resultSet) throws SQLException {
            return mappedColumn.getValue(resultSet);
        }

        public String getProperty() {
            return mappedColumn.getProperty();
        }

        public Method getWriteMethod() {
            return writeMethod;
        }
    }
}
