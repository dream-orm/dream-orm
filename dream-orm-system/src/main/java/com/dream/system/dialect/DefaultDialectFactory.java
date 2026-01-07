package com.dream.system.dialect;

import com.dream.antlr.config.Assist;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.antlr.invoker.ScanInvoker;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultDialectFactory extends AbstractDialectFactory {
    protected ToSQL toSQL;

    @Override
    public MappedStatement compileAntlr(MethodInfo methodInfo, Object arg) throws Exception {
        Configuration configuration = methodInfo.getConfiguration();
        PackageStatement statement = methodInfo.getStatement();
        ScanInvoker.ScanInfo scanInfo = statement.getValue(ScanInvoker.ScanInfo.class);
        String sql;
        List<MarkInvoker.ParamInfo> paramInfoList;
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
        List<MappedParam> mappedParamList = new ArrayList<>();
        if (!ObjectUtil.isNull(paramInfoList)) {
            ParamTypeWrapper paramTypeWrapper = methodInfo.get(ParamTypeWrapper.class);
            if (paramTypeWrapper == null) {
                paramTypeWrapper = new ParamTypeWrapper();
                methodInfo.set(ParamTypeWrapper.class, paramTypeWrapper);
            }
            for (MarkInvoker.ParamInfo paramInfo : paramInfoList) {
                String paramName = paramInfo.getParamName();
                ParamType paramType = null;
                if (paramName != null && !paramName.isEmpty()) {
                    paramType = paramTypeWrapper.get(paramName);
                }
                if (paramType == null) {
                    try {
                        paramType = getParamType(configuration, scanInfo, paramInfo);
                    } catch (TypeHandlerNotFoundException e) {
                        throw new DreamRunTimeException("参数" + paramInfo.getParamName() + "获取类型转换器失败，" + e.getMessage(), e);
                    }
                    paramTypeWrapper.put(paramName, paramType);
                }
                mappedParamList.add(new MappedParam().setParamName(paramName).setParamValue(paramInfo.getParamValue()).setJdbcType(paramType.columnInfo == null ? Types.NULL : paramType.columnInfo.getJdbcType()).setTypeHandler(paramType.getTypeHandler()));
            }
        }
        return new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .command(Command.valueOf(scanInfo.getCommand()))
                .sql(sql)
                .tableSet(scanInfo.getTableScanInfoMap().keySet())
                .mappedParamList(mappedParamList)
                .arg(arg)
                .build();
    }

    protected List<MarkInvoker.ParamInfo> getParamInfoList(Assist assist) {
        MarkInvoker invoker = (MarkInvoker) assist.getInvoker(MarkInvoker.FUNCTION);
        return invoker.getParamInfoList();
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

    protected ParamType getParamType(Configuration configuration, ScanInvoker.ScanInfo scanInfo, MarkInvoker.ParamInfo paramInfo) throws TypeHandlerNotFoundException {
        Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = scanInfo.getTableScanInfoMap();
        TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
        if (tableScanInfoMap != null && tableScanInfoMap.size() == 1) {
            ScanInvoker.TableScanInfo[] tableScanInfos = tableScanInfoMap.values().toArray(new ScanInvoker.TableScanInfo[0]);
            ScanInvoker.TableScanInfo tableScanInfo = tableScanInfos[0];
            String table = tableScanInfo.getTable();
            TableFactory tableFactory = configuration.getTableFactory();
            TableInfo tableInfo = tableFactory.getTableInfo(table);
            if (tableInfo != null) {
                String paramName = paramInfo.getParamName();
                if (paramName != null) {
                    String[] paramNames = paramName.split("\\.");
                    if (paramNames.length > 1) {
                        for (int i = paramNames.length - 1; i >= 0; i--) {
                            if (!Character.isDigit(paramNames[i].charAt(0))) {
                                paramName = paramNames[i];
                                break;
                            }
                        }
                    }
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(paramName);
                    if (columnInfo != null) {
                        TypeHandler typeHandler = columnInfo.getTypeHandler();
                        if (typeHandler == null) {
                            Class<?> javaType = columnInfo.getField().getType();
                            typeHandler = typeHandlerFactory.getTypeHandler(javaType, columnInfo.getJdbcType());
                        }
                        return new ParamType(columnInfo, typeHandler);
                    }
                }
            }
        }
        Object value = paramInfo.getParamValue();
        return new ParamType(null, typeHandlerFactory.getTypeHandler(value == null ? Object.class : value.getClass(), Types.NULL));
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
