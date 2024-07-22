package com.dream.system.core.resultsethandler;

import com.dream.system.cache.CacheKey;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.resultsethandler.config.MappedColumn;
import com.dream.system.core.resultsethandler.config.MappedResult;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.ObjectTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.LowHashSet;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;
import com.dream.util.reflection.factory.ObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class DefaultResultSetHandler implements ResultSetHandler {

    @Override
    public Object result(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException {
        MappedResult mappedResult = getMappedResult(resultSet, mappedStatement);
        ObjectFactory resultObjectFactory = mappedResult.newRowObjectFactory();
        if (mappedResult.isSimple()) {
            while (resultSet.next()) {
                ObjectFactory objectFactory = doSimpleResult(resultSet, mappedStatement, mappedResult);
                Object result = objectFactory.getObject();
                resultObjectFactory.set(null, result);
            }
        } else {
            Map<CacheKey, Object> cacheMap = new HashMap<>(32);
            while (resultSet.next()) {
                ObjectFactory objectFactory = doNestedResult(resultSet, mappedStatement, mappedResult, cacheMap);
                if (objectFactory != null) {
                    Object result = objectFactory.getObject();
                    resultObjectFactory.set(null, result);
                }
            }
            cacheMap.clear();
        }
        return resultObjectFactory.getObject();
    }

    protected ObjectFactory doNestedResult(ResultSet resultSet, MappedStatement mappedStatement, MappedResult mappedResult, Map<CacheKey, Object> cacheMap) throws SQLException {
        Map<String, MappedResult> childMappedResultMap = mappedResult.getChildResultMappingMap();
        ObjectFactory targetObjectFactory = null;
        boolean returnNull = false;
        for (String fieldName : childMappedResultMap.keySet()) {
            MappedResult childMappedResult = childMappedResultMap.get(fieldName);
            ObjectFactory childObjectFactory;
            if (childMappedResult.isSimple()) {
                childObjectFactory = doSimpleResult(resultSet, mappedStatement, childMappedResult);
            } else {
                childObjectFactory = doNestedResult(resultSet, mappedStatement, childMappedResult, cacheMap);
            }
            if (childObjectFactory == null) {
                continue;
            }
            if (targetObjectFactory == null) {
                CacheKey cacheKey = new CacheKey();
                MappedColumn[] primaryList = mappedResult.getPrimaryList();
                if (ObjectUtil.isNull(primaryList)) {
                    primaryList = mappedResult.getMappedColumnList();
                }
                Object[] idList = new Object[primaryList.length + 1];
                idList[0] = mappedResult;
                for (int i = 0; i < primaryList.length; i++) {
                    idList[i + 1] = primaryList[i].getValue(resultSet);
                }
                cacheKey.update(idList);
                targetObjectFactory = (ObjectFactory) cacheMap.get(cacheKey);
                if (targetObjectFactory == null) {
                    targetObjectFactory = doSimpleResult(resultSet, mappedStatement, mappedResult);
                    cacheMap.put(cacheKey, targetObjectFactory);
                } else {
                    returnNull = true;
                }
            }
            Class<? extends Collection> rowType = childMappedResult.getRowType();
            if (NonCollection.class != rowType) {
                Collection rowList = (Collection) targetObjectFactory.get(childMappedResult.getProperty());
                if (rowList == null) {
                    rowList = (Collection) childMappedResult.newRowObjectFactory().getObject();
                    targetObjectFactory.set(childMappedResult.getProperty(), rowList);
                }
                rowList.add(childObjectFactory.getObject());
            } else {
                targetObjectFactory.set(childMappedResult.getProperty(), childObjectFactory.getObject());
            }
        }
        if (returnNull) {
            return null;
        } else {
            return targetObjectFactory;
        }
    }

    protected ObjectFactory doSimpleResult(ResultSet resultSet, MappedStatement mappedStatement, MappedResult mappedResult) throws SQLException {
        MappedColumn[] mappedColumnList = mappedResult.getColumnMappingList();
        ObjectFactory objectFactory = mappedResult.newColObjectFactory();
        for (MappedColumn mappedColumn : mappedColumnList) {
            Object value = mappedColumn.getValue(resultSet);
            objectFactory.set(mappedColumn.getProperty(), value);
        }
        return objectFactory;
    }

    protected MappedResult getMappedResult(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException {
        MappedResult mappedResult = mappedStatement.get(MappedResult.class);
        if (mappedResult == null) {
            synchronized (DefaultResultSetHandler.class) {
                mappedResult = mappedStatement.get(MappedResult.class);
                if (mappedResult == null) {
                    mappedResult = createMappedResult(resultSet, mappedStatement);
                    mappedStatement.set(MappedResult.class, mappedResult);
                }
            }
        }
        return mappedResult;
    }

    protected MappedResult createMappedResult(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Class colType = mappedStatement.getColType();
        if (colType == Object.class && columnCount > 1) {
            colType = HashMap.class;
        }
        MappedResult mappedResult = new MappedResult(mappedStatement.getRowType(), colType, null);
        Set<String> tableSet = mappedStatement.getTableSet();
        for (int i = 1; i <= columnCount; i++) {
            int jdbcType = metaData.getColumnType(i);
            String columnLabel = metaData.getColumnLabel(i);
            String tableName = metaData.getTableName(i);
            ColumnInfo columnInfo = null;
            if (!ObjectUtil.isNull(tableName)) {
                columnInfo = getColumnInfo(mappedStatement, tableName, columnLabel);
            }
            if (columnInfo != null && columnInfo.getJdbcType() != Types.NULL && columnInfo.getJdbcType() != jdbcType) {
                jdbcType = columnInfo.getJdbcType();
            }

            MappedColumn.Builder builder = new MappedColumn
                    .Builder()
                    .index(i)
                    .jdbcType(jdbcType)
                    .table(tableName)
                    .columnLabel(columnLabel)
                    .columnInfo(columnInfo);
            try {
                linkHandler(builder, mappedStatement, mappedResult, new LowHashSet(tableSet));
            } catch (TypeHandlerNotFoundException e) {
                throw new DreamRunTimeException("映射" + mappedStatement.getId() + "失败，映射字段:" + columnLabel + "，表：" + tableName + "，" + e.getMessage(), e);
            }
        }
        return mappedResult;
    }

    protected ColumnInfo getColumnInfo(MappedStatement mappedStatement, String tableName, String columnLabel) {
        Configuration configuration = mappedStatement.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        TableInfo tableInfo;
        if (tableFactory != null) {
            tableInfo = tableFactory.getTableInfo(tableName);
            if (tableInfo != null) {
                return tableInfo.getColumnInfo(columnLabel);
            }
        }
        return null;

    }


    protected boolean linkHandler(MappedColumn.Builder builder, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) throws TypeHandlerNotFoundException {
        Class colType = mappedResult.getColType();
        if (ReflectUtil.isBaseClass(colType)) {
            return linkHandlerForBase(builder, mappedStatement, mappedResult, tableSet);
        } else if (Map.class.isAssignableFrom(colType) || Collection.class.isAssignableFrom(colType)) {
            return linkHandlerForMap(builder, mappedStatement, mappedResult, tableSet);
        } else {
            return linkHandlerForClass(builder, mappedStatement, mappedResult, tableSet);
        }
    }

    protected boolean linkHandlerForBase(MappedColumn.Builder builder, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) throws TypeHandlerNotFoundException {
        builder.typeHandler(mappedStatement
                .getConfiguration()
                .getTypeHandlerFactory()
                .getTypeHandler(mappedResult.getColType()
                        , builder.getJdbcType()));
        mappedResult.add(builder.build());
        return true;
    }

    protected boolean linkHandlerForMap(MappedColumn.Builder builder, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) throws TypeHandlerNotFoundException {
        builder.typeHandler(mappedStatement
                .getConfiguration()
                .getTypeHandlerFactory()
                .getTypeHandler(Object.class
                        , builder.getJdbcType()));
        mappedResult.add(builder.build());
        return true;
    }

    protected boolean linkHandlerForClass(MappedColumn.Builder builder, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) throws TypeHandlerNotFoundException {
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
        Class colType = mappedResult.getColType();
        String curTableName = getTableName(colType);
        List<Field> fieldList = ReflectUtil.findField(colType);
        if (!ObjectUtil.isNull(fieldList)) {
            String property = builder.getProperty();
            boolean lazyLoad = false;
            for (Field field : fieldList) {
                if (!SystemUtil.ignoreField(field)) {
                    String fieldName = field.getName();
                    boolean mappingField = !lazyLoad && (ObjectUtil.isNull(curTableName) || ObjectUtil.isNull(builder.getTable()) || curTableName.equalsIgnoreCase(builder.getTable()));
                    if (mappingField) {
                        if (fieldName.equalsIgnoreCase(property)) {
                            builder.property(fieldName);
                            TypeHandler typeHandler = builder.getTypeHandler();
                            if (typeHandler == null || typeHandler instanceof ObjectTypeHandler) {
                                typeHandler = typeHandlerFactory.getTypeHandler(field.getType(), builder.getJdbcType());
                            }
                            builder.field(field).typeHandler(typeHandler);
                            if (!ObjectUtil.isNull(curTableName)) {
                                mappedResult.add(builder.build());
                                return true;
                            } else {
                                lazyLoad = true;
                            }
                        }
                    }
                    Type genericType = field.getGenericType();
                    String table = getTableName(ReflectUtil.getColType(genericType));
                    boolean mappingTable = !ObjectUtil.isNull(table) && tableSet.contains(table) && (ObjectUtil.isNull(curTableName) || ObjectUtil.isNull(builder.getTable()) || !curTableName.equalsIgnoreCase(builder.getTable()));
                    if (mappingTable) {
                        tableSet.remove(table);
                        Map<String, MappedResult> childMappedResultMap = mappedResult.getChildResultMappingMap();
                        MappedResult childMappedResult = childMappedResultMap.get(fieldName);
                        if (childMappedResult == null) {
                            Class<? extends Collection> rowType = ReflectUtil.getRowType(colType, field);
                            if (rowType == null) {
                                rowType = NonCollection.class;
                            }
                            childMappedResult = new MappedResult(rowType, ReflectUtil.getColType(colType, field), fieldName);
                            if (linkHandler(builder, mappedStatement, childMappedResult, tableSet)) {
                                childMappedResultMap.put(fieldName, childMappedResult);
                                return true;
                            }
                        } else {
                            if (linkHandler(builder, mappedStatement, childMappedResult, tableSet)) {
                                return true;
                            }
                        }
                    }
                }
            }
            if (lazyLoad) {
                mappedResult.add(builder.build());
                return true;
            }
        }
        return false;
    }

    protected String getTableName(Class<?> type) {
        return SystemUtil.getTableName(type);
    }

}
