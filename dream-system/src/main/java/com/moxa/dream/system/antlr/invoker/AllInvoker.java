package com.moxa.dream.system.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.expr.AliasColumnExpr;
import com.moxa.dream.antlr.expr.ListColumnExpr;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToNativeSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.system.util.SystemUtil;
import com.moxa.dream.util.common.LowHashMap;
import com.moxa.dream.util.common.LowHashSet;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class AllInvoker extends AbstractInvoker {

    public static final String FUNCTION = "all";

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        Class colType = methodInfo.getColType();
        Configuration configuration = methodInfo.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        String[] tableList = null;
        if (!ObjectUtil.isNull(columnList)) {
            tableList = new String[columnList.length];
            for (int i = 0; i < columnList.length; i++) {
                if (columnList[i] instanceof SymbolStatement.LetterStatement) {
                    String symbol = ((SymbolStatement.LetterStatement) columnList[i]).getValue();
                    tableList[i] = symbol;
                } else {
                    throw new AntlrException("@all参数类型不合法，不合法参数：'" + new ToNativeSQL().toStr(columnList[i], null, null) + "'");
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
        String selectColumn = String.join(",", queryColumnList);
        ExprReader exprReader = new ExprReader(selectColumn);
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new AliasColumnExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
        Statement statement = listColumnExpr.expr();
        invokerStatement.replaceWith(statement);
        return toSQL.toStr(statement, assist, invokerList);
    }

    protected void getQuery(TableFactory tableFactory, Class colType, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<QueryColumnInfo> queryColumnInfoList, List<String> queryColumnList) throws AntlrException {
        if (Map.class.isAssignableFrom(colType)) {
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
                            boolean find = false;
                            for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoMap.values()) {
                                alias = tableScanInfo.getAlias();
                                TableInfo tableInfo = tableFactory.getTableInfo(tableScanInfo.getTable());
                                ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                                if (columnInfo != null) {
                                    find = true;
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
                            if (!find) {
                                throw new AntlrException("类字段'" + colType.getName() + "." + fieldName + "'未能匹配数据库字段");
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
    public Invoker newInstance() {
        return this;
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
