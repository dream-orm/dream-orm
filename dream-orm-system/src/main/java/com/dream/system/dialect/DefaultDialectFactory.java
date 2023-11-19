package com.dream.system.dialect;

import com.dream.antlr.config.Assist;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.antlr.invoker.ScanInvoker;
import com.dream.system.cache.CacheKey;
import com.dream.system.config.*;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.Types;
import java.util.*;

public class DefaultDialectFactory extends AbstractDialectFactory {
    protected ToSQL toSQL;

    @Override
    public MappedStatement compileAntlr(MethodInfo methodInfo, Object arg) throws Exception {
        Configuration configuration = methodInfo.getConfiguration();
        PackageStatement statement = methodInfo.getStatement();
        ScanInvoker.ScanInfo scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
        String sql;
        List<MarkInvoker.ParamInfo> paramInfoList;
        Map<String, ScanInvoker.ParamScanInfo> paramScanInfoMap;
        if (scanInfo == null) {
            Assist assist = getAssist(methodInfo, arg);
            sql = toSQL.toStr(methodInfo.getStatement(), assist, null);
            scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
            paramInfoList = getParamInfoList(assist);
        } else {
            sql = scanInfo.getSql();
            if (sql == null) {
                Assist assist = getAssist(methodInfo, arg);
                sql = toSQL.toStr(methodInfo.getStatement(), assist, null);
                paramInfoList = getParamInfoList(assist);
            } else {
                paramInfoList = scanInfo.getParamInfoList();
                if (!ObjectUtil.isNull(paramInfoList)) {
                    ObjectWrapper paramWrapper = ObjectWrapper.wrapper(arg);
                    for (MarkInvoker.ParamInfo paramInfo : paramInfoList) {
                        paramInfo.setParamValue(paramWrapper.get(paramInfo.getParamName()));
                    }
                }
            }
        }
        paramScanInfoMap = scanInfo.getParamScanInfoMap();
        List<MappedParam> mappedParamList = new ArrayList<>();
        if (!ObjectUtil.isNull(paramInfoList)) {
            ParamTypeWrapper paramTypeWrapper = methodInfo.get(ParamTypeWrapper.class);
            if (paramTypeWrapper == null) {
                paramTypeWrapper = new ParamTypeWrapper();
                methodInfo.set(ParamTypeWrapper.class, paramTypeWrapper);
            }
            for (MarkInvoker.ParamInfo paramInfo : paramInfoList) {
                String paramName = paramInfo.getParamName();
                ParamType paramType = paramTypeWrapper.get(paramName);
                if (paramType == null) {
                    try {
                        paramType = getParamType(configuration, scanInfo, paramScanInfoMap, paramInfo);
                    } catch (TypeHandlerNotFoundException e) {
                        throw new DreamRunTimeException("参数" + paramInfo.getParamName() + "获取类型转换器失败，" + e.getMessage(), e);
                    }
                    paramTypeWrapper.put(paramName, paramType);
                }
                mappedParamList.add(new MappedParam().setParamName(paramName).setParamValue(paramInfo.getParamValue()).setJdbcType(paramType.columnInfo == null ? Types.NULL : paramType.columnInfo.getJdbcType()).setTypeHandler(paramType.getTypeHandler()));
            }
        }
        CacheKey uniqueKey = getUniqueKey(methodInfo, mappedParamList, sql);
        return new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .mappedSql(new MappedSql(Command.valueOf(scanInfo.getCommand().name()), sql, scanInfo.getTableScanInfoMap().keySet()))
                .mappedParamList(mappedParamList)
                .arg(arg)
                .uniqueKey(uniqueKey)
                .build();
    }

    protected List<MarkInvoker.ParamInfo> getParamInfoList(Assist assist) {
        MarkInvoker invoker = (MarkInvoker) assist.getInvoker(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        return invoker.getParamInfoList();
    }

    protected CacheKey getUniqueKey(MethodInfo methodInfo, List<MappedParam> mappedParamList, String sql) {
        CacheKey uniqueKey = methodInfo.getMethodKey();
        Object[] updateList;
        if (!ObjectUtil.isNull(mappedParamList)) {
            updateList = new Object[mappedParamList.size()];
            for (int i = 0; i < mappedParamList.size(); i++) {
                MappedParam mappedParam = mappedParamList.get(i);
                updateList[i] = mappedParam.getParamValue();
            }
        } else {
            updateList = new Object[0];
        }
        uniqueKey.update(updateList);
        return uniqueKey;
    }

    protected Assist getAssist(MethodInfo methodInfo, Object arg) {
        Map<Class, Object> customMap = new HashMap<>(4);
        Configuration configuration = methodInfo.getConfiguration();
        customMap.put(MethodInfo.class, methodInfo);
        customMap.put(Configuration.class, configuration);
        if (arg == null) {
            arg = new HashMap<>(4);
        }
        customMap.put(ObjectWrapper.class, ObjectWrapper.wrapper(arg));
        return new Assist(configuration.getInvokerFactory(), customMap);
    }

    protected ParamType getParamType(Configuration configuration, ScanInvoker.ScanInfo scanInfo, Map<String, ScanInvoker.ParamScanInfo> paramScanInfoMap, MarkInvoker.ParamInfo paramInfo) throws TypeHandlerNotFoundException {
        TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
        TableFactory tableFactory = configuration.getTableFactory();
        ScanInvoker.ParamScanInfo paramScanInfo = paramScanInfoMap.get(paramInfo.getParamName());
        Object value = paramInfo.getParamValue();
        if (paramScanInfo != null) {
            String table = paramScanInfo.getTable();
            String column = paramScanInfo.getColumn();
            TableInfo tableInfo = null;
            Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = scanInfo.getTableScanInfoMap();
            if (ObjectUtil.isNull(table)) {
                Collection<ScanInvoker.TableScanInfo> tableScanInfoList = tableScanInfoMap.values();
                if (tableFactory != null) {
                    for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoList) {
                        tableInfo = tableFactory.getTableInfo(tableScanInfo.getTable());
                        if (tableInfo != null) {
                            break;
                        }
                    }
                }
            } else {
                ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoMap.get(table);
                if (tableScanInfo != null) {
                    table = tableScanInfo.getTable();
                }
                if (tableFactory != null) {
                    tableInfo = tableFactory.getTableInfo(table);
                }
            }
            int jdbcType = Types.NULL;
            ColumnInfo columnInfo = null;
            if (tableInfo != null) {
                columnInfo = tableInfo.getColumnInfo(column);
                if (columnInfo != null) {
                    jdbcType = columnInfo.getJdbcType();
                }
            }
            return new ParamType(columnInfo, typeHandlerFactory.getTypeHandler(value == null ? Object.class : value.getClass(), jdbcType));
        } else {
            return new ParamType(null, typeHandlerFactory.getTypeHandler(value == null ? Object.class : value.getClass(), Types.NULL));
        }
    }

    public void setToSQL(ToSQL toSQL) {
        this.toSQL = toSQL;
    }

    static class ParamTypeWrapper {
        private final Map<String, ParamType> paramTypeMap = new HashMap<>(4);

        public ParamType get(String param) {
            return paramTypeMap.get(param);
        }

        public void put(String param, ParamType paramType) {
            paramTypeMap.put(param, paramType);
        }
    }

    static class ParamType {
        private final ColumnInfo columnInfo;
        private final TypeHandler typeHandler;

        public ParamType(ColumnInfo columnInfo, TypeHandler typeHandler) {
            this.columnInfo = columnInfo;
            this.typeHandler = typeHandler;
        }

        public ColumnInfo getColumnInfo() {
            return columnInfo;
        }

        public TypeHandler getTypeHandler() {
            return typeHandler;
        }
    }
}
