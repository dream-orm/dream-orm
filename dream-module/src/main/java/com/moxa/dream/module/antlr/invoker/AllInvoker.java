package com.moxa.dream.module.antlr.invoker;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.AliasColumnExpr;
import com.moxa.dream.antlr.expr.ListColumnExpr;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.module.annotation.View;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.table.ColumnInfo;
import com.moxa.dream.module.table.TableInfo;
import com.moxa.dream.module.table.factory.TableFactory;
import com.moxa.dream.util.common.LowHashMap;
import com.moxa.dream.util.common.LowHashSet;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class AllInvoker extends AbstractInvoker {

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
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
                    String symbol = ((SymbolStatement.LetterStatement) columnList[i]).getSymbol();
                    tableList[i] = symbol;
                } else {
                    throw new InvokerException("parameter type is incorrect");
                }
            }
        }
        ScanInvoker scanInvoker = (ScanInvoker) assist.getInvoker(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.SCAN);
        ScanInvoker.ScanInfo scanInfo = scanInvoker.getScanInfo();
        Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = scanInfo.getTableScanInfoMap();
        if (!ObjectUtil.isNull(tableList)) {
            Map<String, ScanInvoker.TableScanInfo> _tableScanInfoMap = scanInfo.getTableScanInfoMap();
            for (String table : tableList) {
                ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoMap.get(table);
                ObjectUtil.requireNonNull(tableScanInfo, "table '" + table + "' was registered");
                _tableScanInfoMap.put(table, tableScanInfo);
            }
            tableScanInfoMap = _tableScanInfoMap;
        }
        Map<String, ScanInvoker.TableScanInfo> lowHashMap = new LowHashMap();
        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoMap.values()) {
            lowHashMap.put(tableScanInfo.getTable(), tableScanInfo);
        }
        List<String> queryColumnList = new ArrayList<>();
        getQuery(tableFactory, colType, lowHashMap, getQueryColumnInfoList(invokerStatement), queryColumnList);
        String selectColumn = String.join(",", queryColumnList);
        ExprReader exprReader = new ExprReader(selectColumn);
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new AliasColumnExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
        Statement statement = listColumnExpr.expr();
        invokerStatement.setStatement(statement);
        return toSQL.toStr(statement, assist, invokerList);
    }

    protected void getQuery(TableFactory tableFactory, Class colType, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<QueryColumnInfo> queryColumnInfoList, List<String> queryColumnList) {
        if (Map.class.isAssignableFrom(colType)) {
            getQueryFromMap(tableFactory, tableScanInfoMap, queryColumnInfoList, queryColumnList);
        } else {
            View viewAnnotation = (View) colType.getDeclaredAnnotation(View.class);
            String table = null;
            if (viewAnnotation != null) {
                table = viewAnnotation.value();
            }
            getQueryFromBean(tableFactory, table, colType, tableScanInfoMap, queryColumnInfoList, queryColumnList);
        }

    }

    protected void getQueryFromMap(TableFactory tableFactory, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<QueryColumnInfo> queryColumnInfoList, List<String> queryColumnList) {
        Set<String> _columnSet = queryColumnInfoList.stream().map(queryColumnInfo -> queryColumnInfo.getColumn()).collect(Collectors.toSet());
        LowHashSet columnSet = new LowHashSet(_columnSet);
        Set<String> _fieldSet = queryColumnInfoList.stream().map(queryColumnInfo -> queryColumnInfo.getAlias()).collect(Collectors.toSet());
        LowHashSet fieldSet = new LowHashSet(_fieldSet);
        Collection<ScanInvoker.TableScanInfo> tableScanInfoList = tableScanInfoMap.values();
        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoList) {
            String table = tableScanInfo.getTable();
            String alias = tableScanInfo.getAlias();
            TableInfo tableInfo = tableFactory.getTableInfo(table);
            ObjectUtil.requireNonNull(tableInfo, "Property 'tableInfo' is required");
            Collection<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();
            List<String> columnList = columnInfoList.stream()
                    .filter(columnInfo -> !columnSet.contains(columnInfo.getColumn())
                            && !fieldSet.contains(columnInfo.getName()))
                    .map(columnInfo -> alias + "." + columnInfo.getColumn())
                    .collect(Collectors.toList());
            queryColumnList.addAll(columnList);
        }
    }

    protected void getQueryFromBean(TableFactory tableFactory, String table, Class colType, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<QueryColumnInfo> queryColumnInfoList, List<String> queryColumnList) {
        TableInfo tableInfo = null;
        String alias = null;
        if (!ObjectUtil.isNull(table)) {
            ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoMap.remove(table);
            if (tableScanInfo == null)
                return;
            tableInfo = tableFactory.getTableInfo(table);
            ObjectUtil.requireNonNull(tableInfo, "Property 'tableInfo' is required");
            alias = tableScanInfo.getAlias();
        }

        List<Field> fieldList = ReflectUtil.findField(colType);
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                String fieldName = field.getName();
                Type genericType = field.getGenericType();
                String fieldTable = getTableName(genericType);
                if (ObjectUtil.isNull(fieldTable)) {
                    if (ObjectUtil.isNull(table)) {
                        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoMap.values()) {
                            alias = tableScanInfo.getAlias();
                            tableInfo = tableFactory.getTableInfo(tableScanInfo.getTable());
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
                                }
                            }
                        }
                    } else {
                        ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                        if (columnInfo != null) {
                            boolean add = true;
                            for (QueryColumnInfo queryColumnInfo : queryColumnInfoList) {
                                String _table = queryColumnInfo.getTable();
                                if ((columnInfo.getColumn().equalsIgnoreCase(queryColumnInfo.getColumn())
                                        || columnInfo.getName().equalsIgnoreCase(queryColumnInfo.getAlias()))
                                        && (ObjectUtil.isNull(_table)
                                        || _table.equalsIgnoreCase(table))) {
                                    add = false;
                                    break;
                                }
                            }
                            if (add) {
                                queryColumnList.add(alias + "." + columnInfo.getColumn());
                            }
                        }
                    }
                } else {
                    getQueryFromBean(tableFactory, fieldTable, ReflectUtil.getColType(colType, field), tableScanInfoMap, queryColumnInfoList, queryColumnList);
                }
            }
        }
    }

    protected String getTableName(Type type) {
        Class colType = ReflectUtil.getColType(type);
        View view = (View) colType.getDeclaredAnnotation(View.class);
        if (view == null)
            return null;
        return view.value();
    }

    protected List<QueryColumnInfo> getQueryColumnInfoList(InvokerStatement invokerStatement) {
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
                SymbolStatement.LetterStatement aliasLetterStatement = aliasStatement.getAlias();
                alias = aliasLetterStatement.getSymbol();
            }
            if (statement instanceof SymbolStatement.LetterStatement) {
                SymbolStatement.LetterStatement letterStatement = (SymbolStatement.LetterStatement) statement;
                String symbol = letterStatement.getSymbol();
                String[] symbols = symbol.split("\\.");
                switch (symbols.length) {
                    case 1:
                        column = symbols[0];
                        break;
                    case 2:
                        table = symbols[0];
                        column = symbols[1];
                        break;
                    case 3:
                        database = symbols[0];
                        table = symbols[1];
                        column = symbols[2];
                        break;
                }
            }
            if (!ObjectUtil.isNull(alias) || !ObjectUtil.isNull(column))
                queryColumnInfoList.add(new QueryColumnInfo(database, table, column, alias));
        }
        return queryColumnInfoList;
    }

    public static class QueryColumnInfo {
        private String database;
        private String table;
        private String column;
        private String alias;

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
