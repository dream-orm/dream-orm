package com.moxa.dream.module.dialect;

import com.moxa.dream.antlr.bind.ResultInfo;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.PackageExpr;
import com.moxa.dream.antlr.expr.SqlExpr;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.invoker.$Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.module.antlr.wrapper.ScanWrapper;
import com.moxa.dream.module.antlr.wrapper.Wrapper;
import com.moxa.dream.module.cache.CacheKey;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapped.MappedParam;
import com.moxa.dream.module.mapped.MappedSql;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.table.ColumnInfo;
import com.moxa.dream.module.table.TableInfo;
import com.moxa.dream.module.table.factory.TableFactory;
import com.moxa.dream.module.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.module.typehandler.handler.TypeHandler;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.*;

public abstract class AbstractDialectFactory implements DialectFactory {
    private static final int SPLIT = 5;
    protected ToSQL toSQL;
    private int split;

    public AbstractDialectFactory() {
        this(SPLIT);
    }

    public AbstractDialectFactory(int split) {
        this.split = split;
        ObjectUtil.requireTrue(split > 0, "Property 'split' must be greater than 0");
    }

    @Override
    public void setDialect(ToSQL toSQL) {
        this.toSQL = toSQL;
    }

    @Override
    public PackageStatement compile(MethodInfo methodInfo) {
        String sql = methodInfo.getSql();
        ExprReader exprReader = new ExprReader(sql, getMyFunctionFactory());
        SqlExpr sqlExpr = new PackageExpr(exprReader);
        PackageStatement statement = (PackageStatement) sqlExpr.expr();
        return statement;
    }

    @Override
    public MappedStatement compile(MethodInfo methodInfo, Object arg) {
        PackageStatement statement = methodInfo.getStatement();
        ResultInfo resultInfo = getResultInfo(methodInfo, statement, arg);
        $Invoker invoker = resultInfo.getSqlInvoker($Invoker.class);
        ScanInvoker.ScanInfo scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
        ParamTypeMap paramTypeMap = methodInfo.get(ParamTypeMap.class);
        if (paramTypeMap == null) {
            synchronized (this) {
                paramTypeMap = methodInfo.get(ParamTypeMap.class);
                if (paramTypeMap == null) {
                    paramTypeMap = new ParamTypeMap();
                    methodInfo.set(ParamTypeMap.class, paramTypeMap);
                }
            }
        }
        List<MappedParam> mappedParamList = null;
        if (invoker != null) {
            Configuration configuration = methodInfo.getConfiguration();
            TableFactory tableFactory = configuration.getTableFactory();
            mappedParamList = new ArrayList<>();
            Map<String, ScanInvoker.ParamScanInfo> paramScanInfoMap = scanInfo.getParamScanInfoMap();
            List<$Invoker.ParamInfo> paramInfoList = invoker.getParamInfoList();
            for ($Invoker.ParamInfo paramInfo : paramInfoList) {
                ParamType paramType = paramTypeMap.get(paramInfo.getParam());
                if (paramType == null) {
                    TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
                    ObjectUtil.requireNonNull(typeHandlerFactory, "Property 'typeHandlerFactory' is required");
                    ScanInvoker.ParamScanInfo paramScanInfo = paramScanInfoMap.get(paramInfo.getParam());
                    String table = paramScanInfo.getTable();
                    String column = paramScanInfo.getColumn();
                    TableInfo tableInfo = null;
                    Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = scanInfo.getTableScanInfoMap();
                    if (ObjectUtil.isNull(table)) {
                        Collection<ScanInvoker.TableScanInfo> tableScanInfoList = tableScanInfoMap.values();
                        ObjectUtil.requireNonNull(tableScanInfoList, "@Function '" + ScanInvoker.class.getName() + "' has no scan table");
                        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoList) {
                            tableInfo = tableFactory.getTableInfo(tableScanInfo.getTable());
                            if (tableInfo != null) {
                                break;
                            }
                        }
                    } else {
                        ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoMap.get(table);
                        if (tableScanInfo != null) {
                            table = tableScanInfo.getTable();
                        }
                        tableInfo = tableFactory.getTableInfo(table);
                    }
                    ObjectUtil.requireNonNull(tableInfo, "tableInfo was not found,table is '" + table + "',column is '" + column + "'");
                    String fieldName = tableInfo.getFieldName(column);
                    if (ObjectUtil.isNull(fieldName))
                        fieldName = column;
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                    int jdbcType = columnInfo.getJdbcType();
                    Object value = paramInfo.getValue();
                    paramTypeMap.put(paramInfo.getParam(),
                            paramType = new ParamType(columnInfo, typeHandlerFactory.getTypeHandler(value == null ? Object.class : value.getClass(), jdbcType)));
                }
                mappedParamList.add(getMappedParam(paramType.getColumnInfo(), paramInfo.getValue(), paramType.getTypeHandler()));
            }
        }
        return new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .mappedSql(new MappedSql(scanInfo.getCommand(), resultInfo.getSql(), scanInfo.getTableScanInfoMap()))
                .mappedParamList(mappedParamList)
                .arg(arg)
                .build();

    }

    protected ResultInfo getResultInfo(MethodInfo methodInfo, PackageStatement statement, Object arg) {
        Map<Class, Object> allCustomMap = new HashMap<>();
        Map<Class, Object> defaultCustomMap = getDefaultCustomMap(methodInfo, arg);
        Map<Class<?>, Object> customMap = getCustomMap(methodInfo, arg);
        allCustomMap.putAll(defaultCustomMap);
        if (!ObjectUtil.isNull(customMap)) {
            for (Class<?> key : customMap.keySet()) {
                allCustomMap.put(key, customMap.get(key));
            }
        }
        List<InvokerFactory> allInvokerFactoryList = new ArrayList<>();
        List<InvokerFactory> defaultInvokerFactoryList = getDefaultInvokerFactoryList();
        List<InvokerFactory> invokerFactoryList = getInvokerFactoryList();
        allInvokerFactoryList.addAll(defaultInvokerFactoryList);
        if (!ObjectUtil.isNull(invokerFactoryList)) {
            allInvokerFactoryList.addAll(invokerFactoryList);
        }
        try {
            ResultInfo resultInfo = toSQL.toResult(statement, allInvokerFactoryList, allCustomMap);
            return resultInfo;
        } catch (InvokerException e) {
            throw new DialectException(e);
        }
    }

    private List<InvokerFactory> getDefaultInvokerFactoryList() {
        List<InvokerFactory> invokerFactoryList = new ArrayList<>();
        invokerFactoryList.add(new AntlrInvokerFactory());
        return invokerFactoryList;
    }

    protected abstract List<InvokerFactory> getInvokerFactoryList();

    private Map<Class, Object> getDefaultCustomMap(MethodInfo methodInfo, Object arg) {
        Map<Class, Object> customMap = new HashMap<>();
        customMap.put(MethodInfo.class, methodInfo);
        customMap.put(ObjectWrapper.class, ObjectWrapper.wrapper(arg));
        return customMap;

    }

    protected abstract <T> Map<Class<? extends T>, T> getCustomMap(MethodInfo methodInfo, Object arg);

    protected MappedParam getMappedParam(ColumnInfo columnInfo, Object paramValue, TypeHandler typeHandler) {
        return new MappedParam(columnInfo.getJdbcType(), paramValue, typeHandler);
    }

    @Override
    public CacheKey getCacheKey(MethodInfo methodInfo) {
        String sql = methodInfo.getSql();
        ObjectUtil.requireTrue(!ObjectUtil.isNull(sql), "Property 'sql' is required");
        char[] charList = sql.toCharArray();
        int index = 0;
        for (int i = 0; i < charList.length; i++) {
            char c;
            if (!Character.isWhitespace(c = charList[i])) {
                charList[index++] = Character.toLowerCase(c);
            }
        }
        if (split > index)
            split = index;
        Object[] hashObj = new Object[split + 2];
        int len = (int) Math.ceil(index / (double) split);
        for (int i = 0; i < split; i++) {
            int sPoint = i * len;
            int size = Math.min((i + 1) * len, index) - sPoint;
            char[] tempChars = new char[size];
            System.arraycopy(charList, sPoint, tempChars, 0, size);
            hashObj[i] = new String(tempChars);
        }
        hashObj[split] = methodInfo.getColType();
        hashObj[split + 1] = methodInfo.getRowType();
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(hashObj);
        return cacheKey;
    }

    protected abstract MyFunctionFactory getMyFunctionFactory();

    @Override
    public void wrapper(PackageStatement statement, MethodInfo methodInfo) {
        List<Wrapper> allWrapperList = new ArrayList<>();
        List<Wrapper> defaultWrapperList = getDefaultWrapList();
        List<Wrapper> wrapperList = getWrapList();
        if (!ObjectUtil.isNull(wrapperList)) {
            allWrapperList.addAll(wrapperList);
        }
        allWrapperList.addAll(defaultWrapperList);
        for (Wrapper wrapper : allWrapperList) {
            wrapper.wrapper(statement, methodInfo);
        }
    }

    private List<Wrapper> getDefaultWrapList() {
        return List.of(new ScanWrapper());
    }

    protected abstract List<Wrapper> getWrapList();


    static class ParamTypeMap {
        private Map<String, ParamType> paramTypeMap = new HashMap<>();

        public ParamType get(String param) {
            return paramTypeMap.get(param);
        }

        public void put(String param, ParamType paramType) {
            paramTypeMap.put(param, paramType);
        }
    }

    static class ParamType {
        private ColumnInfo columnInfo;
        private TypeHandler typeHandler;

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
