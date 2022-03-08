package com.moxa.dream.engine.result;

import com.moxa.dream.engine.executor.Executor;
import com.moxa.dream.module.annotation.View;
import com.moxa.dream.module.cache.CacheKey;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.dialect.DialectFactory;
import com.moxa.dream.module.mapped.MappedColumn;
import com.moxa.dream.module.mapped.MappedResult;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.mapper.EachInfo;
import com.moxa.dream.module.mapper.MapperFactory;
import com.moxa.dream.module.table.ColumnInfo;
import com.moxa.dream.module.table.TableInfo;
import com.moxa.dream.module.type.handler.TypeHandler;
import com.moxa.dream.module.util.NonCollection;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class DefaultResultSetHandler implements ResultSetHandler {
    private MapperFactory mapperFactory;
    private DialectFactory dialectFactory;
    private Executor executor;

    public DefaultResultSetHandler(Configuration configuration, Executor executor) {
        this.mapperFactory = configuration.getMapperFactory();
        this.dialectFactory = configuration.getDialectFactory();
        this.executor = executor;

    }

    @Override
    public Object result(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException {
        MappedResult mappedResult = getMappedResult(resultSet, mappedStatement);
        Collection collection = getCollection(mappedStatement);
        if (mappedResult.isSimple()) {
            while (resultSet.next()) {
                Object result = doSimpleResult(resultSet, mappedStatement, mappedResult);
                collection.add(result);
                eachList(result, mappedStatement.getEachInfoList());
            }
        } else {
            Map<CacheKey, Object> cacheMap = new HashMap<>();
            while (resultSet.next()) {
                Object result = doNestedResult(resultSet, mappedStatement, mappedResult, cacheMap);
                if (result != null) {
                    collection.add(result);
                    eachList(result, mappedStatement.getEachInfoList());
                }
            }
            cacheMap.clear();
        }
        return collection instanceof NonCollection ? ((NonCollection<?>) collection).toObject() : collection.isEmpty()?null:collection;
    }

    protected Object doNestedResult(ResultSet resultSet, MappedStatement mappedStatement, MappedResult mappedResult, Map<CacheKey, Object> cacheMap) throws SQLException {
        Map<String, MappedResult> childResultMappingMap = mappedResult.getChildResultMappingMap();
        Object target = null;
        ObjectWrapper targetWrapper = null;
        for (String fieldName : childResultMappingMap.keySet()) {
            MappedResult childMappedResult = childResultMappingMap.get(fieldName);
            Object childObject;
            boolean simple;
            if (simple = childMappedResult.isSimple()) {
                childObject = doSimpleResult(resultSet, mappedStatement, childMappedResult);
            } else {
                childObject = doNestedResult(resultSet, mappedStatement, mappedResult, cacheMap);
            }
            if (!simple && childObject == null)
                continue;
            if (targetWrapper == null) {
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
                Object curObject = cacheMap.get(cacheKey);
                if (curObject == null) {
                    target = curObject = doSimpleResult(resultSet, mappedStatement, mappedResult);
                    cacheMap.put(cacheKey, curObject);
                }
                targetWrapper = ObjectWrapper.wrapper(curObject);
            }
            Object oldValue = targetWrapper.set(childMappedResult.getLink(), childObject);
            ObjectUtil.requireTrue(oldValue == null, "数据返回多条，但期待一条");
        }
        return target;
    }

    protected Object doSimpleResult(ResultSet resultSet, MappedStatement mappedStatement, MappedResult mappedResult) throws SQLException {
        MappedColumn[] mappedColumnList = mappedResult.getColumnMappingList();
        ObjectWrapper objectWrapper = ObjectWrapper.wrapper(mappedResult.getColType());
        for (MappedColumn mappedColumn : mappedColumnList) {
            mappedColumn.linkObject(resultSet, objectWrapper);
        }
        return objectWrapper.getObject();
    }

    protected MappedResult getMappedResult(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException {
        MappedResult mappedResult = mappedStatement.get(MappedResult.class);
        if (mappedResult == null) {
            synchronized (this.getClass().getName()) {
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
        if (colType == null)
            colType = Object.class;
        if (colType == Object.class && columnCount > 1)
            colType = HashMap.class;
        MappedResult mappedResult = new MappedResult(colType, null);
        for (int i = 1; i <= columnCount; i++) {
            int jdbcType = metaData.getColumnType(i);
            String columnLabel = metaData.getColumnLabel(i);
            String tableName = metaData.getTableName(i);
            String link = getLink(mappedStatement, tableName, columnLabel);
            boolean primary = isPrimary(tableName, columnLabel, mappedStatement);
            MappedColumn mappedColumn = new MappedColumn(i, jdbcType, tableName, primary);
            mappedColumn.setLink(link);
            boolean success = linkHandler(mappedColumn, mappedStatement, mappedResult, mappedStatement.getTableSet());
            ObjectUtil.requireTrue(success, "Property '" + link + "' mapping failure");
        }
        return mappedResult;
    }

    protected boolean isPrimary(String tableName, String columnLabel, MappedStatement mappedStatement) {
        if (ObjectUtil.isNull(tableName))
            return false;
        Configuration configuration = mappedStatement.getConfiguration();
        TableInfo tableInfo = configuration.getTableFactory().getTableInfo(tableName);
        if (tableInfo == null)
            return false;
        String fieldName = tableInfo.getFieldName(columnLabel);
        if (ObjectUtil.isNull(fieldName))
            return false;
        ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
        return columnInfo.isPrimary();
    }

    protected String getLink(MappedStatement mappedStatement, String tableName, String columnLabel) {
        Configuration configuration = mappedStatement.getConfiguration();
        TableInfo tableInfo = configuration.getTableFactory().getTableInfo(tableName);
        String link = null;
        if (tableInfo != null) {
            link = tableInfo.getFieldName(columnLabel);
        }
        if (ObjectUtil.isNull(link))
            return columnLabel;
        else return link;
    }


    protected boolean linkHandler(MappedColumn mappedColumn, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) {
        Class colType = mappedResult.getColType();
        if (ReflectUtil.isBaseClass(colType))
            return linkHandlerForBase(mappedColumn, mappedStatement, mappedResult, tableSet);
        else if (Map.class.isAssignableFrom(colType)) {
            return linkHandlerForMap(mappedColumn, mappedStatement, mappedResult, tableSet);
        } else {
            return linkHandlerForClass(mappedColumn, mappedStatement, mappedResult, tableSet);
        }
    }

    protected boolean linkHandlerForBase(MappedColumn mappedColumn, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) {
        mappedColumn.setTypeHandler(mappedStatement.getConfiguration().getTypeHandlerFactory().getTypeHandler(mappedResult.getColType(), mappedColumn.getJdbcType()));
        mappedResult.add(mappedColumn);
        return true;
    }

    protected boolean linkHandlerForMap(MappedColumn mappedColumn, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) {
        mappedColumn.setTypeHandler(mappedStatement.getConfiguration().getTypeHandlerFactory().getTypeHandler(Object.class, mappedColumn.getJdbcType()));
        mappedResult.add(mappedColumn);
        return true;
    }

    protected boolean linkHandlerForClass(MappedColumn mappedColumn, MappedStatement mappedStatement, MappedResult mappedResult, Set<String> tableSet) {
        Class colType = mappedResult.getColType();
        String curTableName = getTableName(colType);
        List<Field> fieldList = ReflectUtil.findField(colType);
        if (!ObjectUtil.isNull(fieldList)) {
            boolean lazyLoad = false;
            for (Field field : fieldList) {
                String fieldName = field.getName();
                String link = mappedColumn.getLink();
                if (ObjectUtil.isNull(curTableName) || ObjectUtil.isNull(mappedColumn.getTable()) || curTableName.equalsIgnoreCase(mappedColumn.getTable())) {
                    if (fieldName.equalsIgnoreCase(link)) {
                        if (!fieldName.equals(link))
                            mappedColumn.setLink(fieldName);
                        TypeHandler typeHandler = mappedStatement.getConfiguration().getTypeHandlerFactory().getTypeHandler(field.getType(), mappedColumn.getJdbcType());
                        mappedColumn.setTypeHandler(typeHandler);
                        if (!ObjectUtil.isNull(curTableName)) {
                            mappedResult.add(mappedColumn);
                            return true;
                        } else {
                            lazyLoad = true;
                        }
                    }
                }
                Type genericType = field.getGenericType();
                String table = getTableName(genericType);
                if (!ObjectUtil.isNull(table) && tableSet.contains(table)) {
                    Map<String, MappedResult> childResultMappingMap = mappedResult.getChildResultMappingMap();
                    MappedResult childResultMapping = childResultMappingMap.get(fieldName);
                    if (childResultMapping == null) {
                        Class<? extends Collection> collectionType = ReflectUtil.getRowType(colType, field);
                        childResultMapping = new MappedResult(ReflectUtil.getColType(colType, field), collectionType == null ? fieldName : (fieldName + "[-1]"));
                        if (linkHandler(mappedColumn, mappedStatement, childResultMapping, tableSet)) {
                            childResultMappingMap.put(fieldName, childResultMapping);
                            return true;
                        }
                    } else {
                        if (linkHandler(mappedColumn, mappedStatement, childResultMapping, tableSet)) {
                            return true;
                        }
                    }
                }
            }
            if (lazyLoad) {
                mappedResult.add(mappedColumn);
                return true;
            }
        }
        return false;
    }

    protected String getTableName(Type type) {
        Class colType = ReflectUtil.getColType(type);
        View view = (View) colType.getDeclaredAnnotation(View.class);
        if (view == null)
            return null;
        return view.value();
    }

    protected Collection getCollection(MappedStatement mappedStatement) {
        Class<? extends Collection> collectionType = mappedStatement.getRowType();
        if (collectionType == null) {
            return new ArrayList();
        } else {
            return ReflectUtil.create(collectionType);
        }
    }

    protected void eachList(Object object, List<EachInfo> eachInfoList) {
        if (!ObjectUtil.isNull(eachInfoList)) {
            ObjectWrapper wrapper = ObjectWrapper.wrapper(object);
            for (EachInfo eachInfo : eachInfoList) {
                Class type = eachInfo.getType();
                Method method = eachInfo.getMethod();
                Map<String, String> argMap = eachInfo.getArgMap();
                String field = eachInfo.getField();
                Object mapper = mapperFactory.getMapper(type, (methodInfo, arg, args) -> {
                    if (argMap != null) {
                        ObjectWrapper argWrapper = ObjectWrapper.wrapper(arg);
                        for (String paramName : argMap.keySet()) {
                            String valueName = argMap.get(paramName);
                            Object value = wrapper.get(valueName);
                            argWrapper.set(paramName, value);
                        }
                    }
                    MappedStatement mappedStatement = dialectFactory.compile(methodInfo, arg);
                    return executor.query(mappedStatement);
                });
                Object result;
                try {
                    result = method.invoke(mapper, eachInfo.getArgs());
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
                if (!ObjectUtil.isNull(field)) {
                    wrapper.set(field, result);
                }
            }
        }
    }
}
