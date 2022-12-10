package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.*;
import com.moxa.dream.system.antlr.factory.DefaultInvokerFactory;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.antlr.invoker.$Invoker;
import com.moxa.dream.system.antlr.invoker.ScanInvoker;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.*;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.system.typehandler.TypeHandlerNotFoundException;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultDialectFactory implements DialectFactory {
    protected DbType dbType = DbType.AUTO;
    protected ToSQL toSQL;
    protected Map<Class<? extends InvokerFactory>, InvokerFactory> invokerFactoryMap = new HashMap();

    public DefaultDialectFactory() {
        invokerFactoryMap.put(SystemInvokerFactory.class, new SystemInvokerFactory());
        invokerFactoryMap.put(DefaultInvokerFactory.class, new DefaultInvokerFactory());
    }

    public void addInvokerFactory(InvokerFactory invokerFactory) {
        invokerFactoryMap.put(invokerFactory.getClass(), invokerFactory);
    }

    public <T extends InvokerFactory> T getInvokerFactory(Class<T> invokerFactoryType) {
        return (T) invokerFactoryMap.get(invokerFactoryType);
    }

    @Override
    public synchronized MappedStatement compile(MethodInfo methodInfo, Object arg) throws Exception {
        Configuration configuration = methodInfo.getConfiguration();
        PackageStatement statement = methodInfo.getStatement();
        ScanInvoker.ScanInfo scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
        String sql;
        List<$Invoker.ParamInfo> paramInfoList;
        Map<String, ScanInvoker.ParamScanInfo> paramScanInfoMap;
        if (scanInfo == null) {
            Assist assist = getAssist(methodInfo, arg);
            sql = getSql(methodInfo, assist);
            scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
            paramInfoList = getParamInfoList(assist);
        } else {
            sql = scanInfo.getSql();
            if (sql == null) {
                Assist assist = getAssist(methodInfo, arg);
                sql = getSql(methodInfo, assist);
                paramInfoList = getParamInfoList(assist);
            } else {
                paramInfoList = scanInfo.getParamInfoList();
                if (!ObjectUtil.isNull(paramInfoList)) {
                    ObjectWrapper paramWrapper = ObjectWrapper.wrapper(arg);
                    for ($Invoker.ParamInfo paramInfo : paramInfoList) {
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
            for ($Invoker.ParamInfo paramInfo : paramInfoList) {
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
                mappedParamList.add(new MappedParam().setParamValue(paramInfo.getParamValue()).setJdbcType(paramType.columnInfo == null ? Types.NULL : paramType.columnInfo.getJdbcType()).setTypeHandler(paramType.getTypeHandler()));
            }
        }
        CacheKey uniqueKey = getUniqueKey(methodInfo, mappedParamList, sql);
        return new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .mappedSql(new MappedSql(scanInfo.getCommand().name(), sql, scanInfo.getTableScanInfoMap().keySet()))
                .mappedParamList(mappedParamList)
                .arg(arg)
                .uniqueKey(uniqueKey)
                .build();
    }

    protected String getSql(MethodInfo methodInfo, Assist assist) throws AntlrException {
        if (toSQL == null) {
            toSQL = getToSQL(methodInfo.getConfiguration());
        }
        return toSQL.toStr(methodInfo.getStatement(), assist, null);
    }

    protected List<$Invoker.ParamInfo> getParamInfoList(Assist assist) throws AntlrException {
        $Invoker invoker = ($Invoker) assist.getInvoker(SystemInvokerFactory.NAMESPACE, SystemInvokerFactory.$);
        if (invoker != null) {
            return invoker.getParamInfoList();
        } else {
            return null;
        }
    }

    protected CacheKey getUniqueKey(MethodInfo methodInfo, List<MappedParam> mappedParamList, String sql) {
        CacheKey uniqueKey = methodInfo.getMethodKey();
        Object[] updateList;
        if (!ObjectUtil.isNull(mappedParamList)) {
            updateList = new Object[mappedParamList.size() + 2];
            updateList[0] = sql;
            updateList[1] = sql.length();
            for (int i = 0; i < mappedParamList.size(); i++) {
                MappedParam mappedParam = mappedParamList.get(i);
                updateList[i + 2] = mappedParam.getParamValue();
            }
        } else {
            updateList = new Object[2];
            updateList[0] = sql;
            updateList[1] = sql.length();
        }
        uniqueKey.update(updateList);
        return uniqueKey;
    }

    protected Assist getAssist(MethodInfo methodInfo, Object arg) {
        Map<Class, Object> customMap = new HashMap<>();
        customMap.put(MethodInfo.class, methodInfo);
        if (arg == null) {
            arg = new HashMap<>();
        }
        customMap.put(ObjectWrapper.class, ObjectWrapper.wrapper(arg));
        return new Assist(invokerFactoryMap.values(), customMap);
    }

    protected ParamType getParamType(Configuration configuration, ScanInvoker.ScanInfo scanInfo, Map<String, ScanInvoker.ParamScanInfo> paramScanInfoMap, $Invoker.ParamInfo paramInfo) throws TypeHandlerNotFoundException {
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
                String fieldName = tableInfo.getFieldName(column);
                if (!ObjectUtil.isNull(fieldName)) {
                    columnInfo = tableInfo.getColumnInfo(fieldName);
                    if (columnInfo != null) {
                        jdbcType = columnInfo.getJdbcType();
                    }
                }
            }
            return new ParamType(columnInfo, typeHandlerFactory.getTypeHandler(value == null ? Object.class : value.getClass(), jdbcType));
        } else {
            return new ParamType(null, typeHandlerFactory.getTypeHandler(value == null ? Object.class : value.getClass(), Types.NULL));
        }
    }

    @Override
    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    @Override
    public void addInvoker(String invokerName, Invoker invoker) {
        getInvokerFactory(DefaultInvokerFactory.class).addInvoker(invokerName, invoker);
    }

    public void setToSQL(ToSQL toSQL) {
        this.toSQL = toSQL;
    }

    protected ToSQL getToSQL(Configuration configuration) {
        dbType = getDbType();
        if (dbType == DbType.AUTO) {
            DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
            DataSource dataSource = dataSourceFactory.getDataSource();
            dbType = getDbType(dataSource);
        }
        switch (dbType) {
            case MYSQL:
                return new ToMYSQL();
            case MSSQL:
                return new ToMSSQL();
            case PGSQL:
                return new ToPGSQL();
            case ORACLE:
                return new ToORACLE();
            default:
                throw new DreamRunTimeException(getDbType() + "类型尚未支持");
        }
    }

    protected DbType getDbType(DataSource dataSource) {
        DbType dbType = null;
        List<Method> methodList = ReflectUtil.findMethod(dataSource.getClass()).stream().filter(method -> "getDriverClassName".equals(method.getName()) && method.getParameterCount() == 0 && method.getReturnType() == String.class).collect(Collectors.toList());
        if (ObjectUtil.isNull(methodList)) {
            throw new DreamRunTimeException("当前数据库" + dataSource.getClass().getName() + "不支持自动获取数据类型");
        }
        Method method = methodList.get(0);
        String driverClassName;
        try {
            driverClassName = (String) method.invoke(dataSource);
        } catch (Exception e) {
            throw new DreamRunTimeException("当前数据库" + dataSource.getClass().getName() + "调用方法" + method.getName() + "失败", e);
        }
        if (driverClassName == null) {
            throw new DreamRunTimeException("当前数据库" + dataSource.getClass().getName() + "调用方法" + method.getName() + "返回值为null，不支持自动获取数据库类型");
        }
        switch (driverClassName) {
            case "com.mysql.jdbc.Driver":
            case "com.mysql.cj.jdbc.Driver":
                dbType = DbType.MYSQL;
                break;
            case "org.postgresql.Driver":
                dbType = DbType.PGSQL;
                break;
            case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
                dbType = DbType.MSSQL;
                break;
            case "oracle.jdbc.driver.OracleDriver":
                dbType = DbType.ORACLE;
                break;
            default:
                throw new DreamRunTimeException("驱动" + driverClassName + "尚未支持自动获取数据库类型");
        }
        return dbType;
    }

    static class ParamTypeWrapper {
        private final Map<String, ParamType> paramTypeMap = new HashMap<>();

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
