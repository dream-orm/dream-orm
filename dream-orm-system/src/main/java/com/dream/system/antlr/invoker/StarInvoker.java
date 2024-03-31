package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToNativeSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.LowHashMap;
import com.dream.util.common.LowHashSet;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class StarInvoker extends AbstractInvoker {

    public static final String FUNCTION = "*";

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        Class colType = methodInfo.getColType();
        Configuration configuration = methodInfo.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        String[] tableList = null;
        ListColumnStatement listColumnStatement = (ListColumnStatement) invokerStatement.getParamStatement();
        if (listColumnStatement != null) {
            Statement[] columnList = listColumnStatement.getColumnList();
            tableList = new String[columnList.length];
            for (int i = 0; i < columnList.length; i++) {
                if (columnList[i] instanceof SymbolStatement.LetterStatement) {
                    String symbol = ((SymbolStatement.LetterStatement) columnList[i]).getValue();
                    tableList[i] = symbol;
                } else {
                    throw new AntlrException("@" + FUNCTION + "参数类型不合法，不合法参数：'" + new ToNativeSQL().toStr(columnList[i], null, null) + "'");
                }
            }
        }
        ScanInvoker scanInvoker = (ScanInvoker) assist.getInvoker(ScanInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        ScanInvoker.ScanInfo scanInfo = scanInvoker.getScanInfo();
        Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = scanInfo.getTableScanInfoMap();
        if (!ObjectUtil.isNull(tableList)) {
            Map<String, ScanInvoker.TableScanInfo> scanInfoMap = new LowHashMap<>();
            for (String table : tableList) {
                ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoMap.get(table);
                if (tableScanInfo == null) {
                    throw new DreamRunTimeException("函数@" + this.function() + ":" + this.namespace() + "参数值" + table + "未出现在操作表语句");
                }
                scanInfoMap.put(table, tableScanInfo);
            }
            tableScanInfoMap = scanInfoMap;
        }
        Map<String, ScanInvoker.TableScanInfo> lowHashMap = new LowHashMap();
        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoMap.values()) {
            lowHashMap.put(tableScanInfo.getTable(), tableScanInfo);
        }
        List<String> queryColumnList = new ArrayList<>();
        getQuery(tableFactory, colType, lowHashMap, getQueryColumnInfoList(assist, toSQL, invokerList, invokerStatement), queryColumnList);
        ListColumnStatement selectListColumnStatement = AntlrUtil.listColumnStatement(",", queryColumnList.toArray(new String[queryColumnList.size()]));
        invokerStatement.replaceWith(selectListColumnStatement);
        return toSQL.toStr(selectListColumnStatement, assist, invokerList);
    }

    protected void getQuery(TableFactory tableFactory, Class colType, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<QueryColumnInfo> queryColumnInfoList, List<String> queryColumnList) throws AntlrException {
        if (Map.class.isAssignableFrom(colType) || colType == Object.class) {
            getQueryFromMap(tableFactory, tableScanInfoMap, queryColumnInfoList, queryColumnList);
        } else {
            String table = getTableName(colType);
            getQueryFromBean(tableFactory, table, colType, tableScanInfoMap, queryColumnInfoList, queryColumnList);
        }
    }

    protected void getQueryFromMap(TableFactory tableFactory, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<QueryColumnInfo> queryColumnInfoList, List<String> queryColumnList) {
        Set<String> set = queryColumnInfoList.stream().map(queryColumnInfo -> queryColumnInfo.getColumn()).collect(Collectors.toSet());
        LowHashSet columnSet = new LowHashSet(set);
        Set<String> aliasSet = queryColumnInfoList.stream().map(queryColumnInfo -> queryColumnInfo.getAlias()).collect(Collectors.toSet());
        LowHashSet fieldSet = new LowHashSet(aliasSet);
        Collection<ScanInvoker.TableScanInfo> tableScanInfoList = tableScanInfoMap.values();
        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoList) {
            String table = tableScanInfo.getTable();
            String alias = tableScanInfo.getAlias();
            TableInfo tableInfo = tableFactory.getTableInfo(table);
            if (tableInfo == null) {
                throw new DreamRunTimeException("TableFactory不存在表" + table);
            }
            Collection<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();
            List<String> columnList = columnInfoList.stream()
                    .filter(columnInfo -> !columnSet.contains(columnInfo.getColumn())
                            && !fieldSet.contains(columnInfo.getName()))
                    .map(columnInfo -> alias + "." + columnInfo.getColumn())
                    .collect(Collectors.toList());
            queryColumnList.addAll(columnList);
        }
    }

    protected void getQueryFromBean(TableFactory tableFactory, String table, Class colType, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<QueryColumnInfo> queryColumnInfoList, List<String> queryColumnList) throws AntlrException {
        TableInfo rootTableInfo = null;
        String alias = null;
        if (!ObjectUtil.isNull(table)) {
            ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoMap.remove(table);
            if (tableScanInfo == null) {
                return;
            }
            rootTableInfo = tableFactory.getTableInfo(table);
            if (rootTableInfo == null) {
                throw new DreamRunTimeException("TableFactory不存在表" + table);
            }
            alias = tableScanInfo.getAlias();
        }
        List<Field> fieldList = ReflectUtil.findField(colType);
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (!ignore(field)) {
                    String fieldName = field.getName();
                    Type genericType = field.getGenericType();
                    String fieldTable = getTableName(ReflectUtil.getColType(genericType));
                    if (ObjectUtil.isNull(fieldTable)) {
                        if (rootTableInfo == null) {
                            for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoMap.values()) {
                                alias = tableScanInfo.getAlias();
                                TableInfo tableInfo = tableFactory.getTableInfo(tableScanInfo.getTable());
                                ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                                if (columnInfo != null) {
                                    boolean add = true;
                                    for (QueryColumnInfo queryColumnInfo : queryColumnInfoList) {
                                        if (columnInfo.getColumn().equalsIgnoreCase(queryColumnInfo.getColumn())
                                                || columnInfo.getName().equalsIgnoreCase(queryColumnInfo.getAlias())) {
                                            add = false;
                                            break;
                                        }
                                    }
                                    if (add) {
                                        queryColumnList.add(alias + "." + columnInfo.getColumn());
                                        break;
                                    }
                                }
                            }
                        } else {
                            ColumnInfo columnInfo = rootTableInfo.getColumnInfo(fieldName);
                            if (columnInfo == null) {
                                throw new AntlrException("数据表映射类不存在" + fieldName + "属性");
                            }
                            boolean add = true;
                            for (QueryColumnInfo queryColumnInfo : queryColumnInfoList) {
                                String queryTable = queryColumnInfo.getTable();
                                boolean hasAdd = (columnInfo.getColumn().equalsIgnoreCase(queryColumnInfo.getColumn())
                                        || columnInfo.getName().equalsIgnoreCase(queryColumnInfo.getAlias()))
                                        && (ObjectUtil.isNull(queryTable)
                                        || queryTable.equalsIgnoreCase(alias));
                                if (hasAdd) {
                                    add = false;
                                    break;
                                }
                            }
                            if (add) {
                                queryColumnList.add(alias + "." + columnInfo.getColumn());
                            }
                        }
                    } else {
                        getQueryFromBean(tableFactory, fieldTable, ReflectUtil.getColType(colType, field), tableScanInfoMap, queryColumnInfoList, queryColumnList);
                    }
                }
            }
        }
    }

    protected boolean ignore(Field field) {
        return SystemUtil.ignoreField(field);
    }

    protected String getTableName(Class<?> type) {
        return SystemUtil.getTableName(type);
    }

    protected List<QueryColumnInfo> getQueryColumnInfoList(Assist assist, ToSQL toSQL, List<Invoker> invokerList, InvokerStatement invokerStatement) throws AntlrException {
        Statement parentStatement = invokerStatement.getParentStatement();
        ListColumnStatement listColumnStatement = (ListColumnStatement) parentStatement;
        Statement[] columnList = listColumnStatement.getColumnList();
        List<QueryColumnInfo> queryColumnInfoList = new ArrayList<>();
        for (Statement statement : columnList) {
            String database = null;
            String table = null;
            String column = null;
            String alias = null;
            if (statement instanceof AliasStatement) {
                AliasStatement aliasStatement = (AliasStatement) statement;
                statement = aliasStatement.getColumn();
                alias = toSQL.toStr(aliasStatement.getAlias(), assist, invokerList);
            }
            if (statement instanceof SymbolStatement) {
                SymbolStatement symbolStatement = (SymbolStatement) statement;
                column = symbolStatement.getValue();
            } else if (statement instanceof ListColumnStatement) {
                ListColumnStatement columnStatements = (ListColumnStatement) statement;
                Statement[] columnLists = columnStatements.getColumnList();
                if (columnLists.length == 3) {
                    database = ((SymbolStatement) columnLists[0]).getValue();
                    table = ((SymbolStatement) columnLists[1]).getValue();
                    column = ((SymbolStatement) columnLists[2]).getValue();
                } else if (columnLists.length == 2) {
                    table = ((SymbolStatement) columnLists[0]).getValue();
                    column = ((SymbolStatement) columnLists[1]).getValue();
                }
            }
            if (!ObjectUtil.isNull(column)) {
                queryColumnInfoList.add(new QueryColumnInfo(database, table, column, alias));
            }
        }
        return queryColumnInfoList;
    }


    @Override
    public String function() {
        return FUNCTION;
    }

    public static class QueryColumnInfo {
        private final String database;
        private final String table;
        private final String column;
        private final String alias;

        public QueryColumnInfo(String database, String table, String column, String alias) {
            this.database = database;
            this.table = table;
            this.column = column;
            this.alias = alias;
        }

        public String getDatabase() {
            return database;
        }

        public String getTable() {
            return table;
        }

        public String getColumn() {
            return column;
        }

        public String getAlias() {
            return alias;
        }
    }
}
