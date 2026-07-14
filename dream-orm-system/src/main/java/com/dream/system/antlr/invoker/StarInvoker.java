package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.ColumnExpr;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToNativeSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.annotation.Value;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.LowHashMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        ScanInvoker scanInvoker = (ScanInvoker) assist.getInvoker(ScanInvoker.FUNCTION);
        ScanInvoker.ScanInfo scanInfo = scanInvoker.getScanInfo();
        Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap = scanInfo.getTableScanInfoMap();
        if (!ObjectUtil.isNull(tableList)) {
            Map<String, ScanInvoker.TableScanInfo> scanInfoMap = new LowHashMap<>();
            for (String table : tableList) {
                ScanInvoker.TableScanInfo tableScanInfo = tableScanInfoMap.get(table);
                if (tableScanInfo == null) {
                    throw new DreamRunTimeException("函数@" + this.function() + "参数值" + table + "未出现在操作表语句");
                }
                scanInfoMap.put(table, tableScanInfo);
            }
            tableScanInfoMap = scanInfoMap;
        }
        Map<String, ScanInvoker.TableScanInfo> lowHashMap = new LowHashMap();
        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoMap.values()) {
            lowHashMap.put(tableScanInfo.getTable(), tableScanInfo);
        }
        List<Statement> queryColumnList = new ArrayList<>();
        getQuery(tableFactory, colType, lowHashMap, queryColumnList);
        ListColumnStatement selectListColumnStatement = AntlrUtil.listColumnStatement(",", queryColumnList.toArray(new Statement[queryColumnList.size()]));
        invokerStatement.setActualStatement(selectListColumnStatement);
        return toSQL.toStr(selectListColumnStatement, assist, invokerList);
    }

    protected void getQuery(TableFactory tableFactory, Class colType, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<Statement> queryColumnList) throws AntlrException {
        if (Map.class.isAssignableFrom(colType) || colType == Object.class) {
            getQueryFromMap(tableFactory, tableScanInfoMap, queryColumnList);
        } else {
            String table = getTableName(colType);
            getQueryFromBean(tableFactory, table, colType, tableScanInfoMap, queryColumnList);
        }
    }

    protected void getQueryFromMap(TableFactory tableFactory, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<Statement> queryColumnList) {
        Collection<ScanInvoker.TableScanInfo> tableScanInfoList = tableScanInfoMap.values();
        for (ScanInvoker.TableScanInfo tableScanInfo : tableScanInfoList) {
            String table = tableScanInfo.getTable();
            String alias = tableScanInfo.getAlias();
            TableInfo tableInfo = tableFactory.getTableInfo(table);
            if (tableInfo == null) {
                throw new DreamRunTimeException("TableFactory不存在表" + table);
            }
            Collection<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();
            List<Statement> columnList = columnInfoList.stream()
                    .map(columnInfo -> AntlrUtil.aliasStatement(AntlrUtil.listColumnStatement(".", new SymbolStatement.SingleMarkStatement(alias), new SymbolStatement.SingleMarkStatement(columnInfo.getColumn())), new SymbolStatement.SingleMarkStatement(columnInfo.getName())))
                    .collect(Collectors.toList());
            queryColumnList.addAll(columnList);
        }
    }

    protected void getQueryFromBean(TableFactory tableFactory, String table, Class colType, Map<String, ScanInvoker.TableScanInfo> tableScanInfoMap, List<Statement> queryColumnList) throws AntlrException {
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
                if (!SystemUtil.ignoreField(field)) {
                    Value value = field.getAnnotation(Value.class);
                    if (value == null) {
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
                                        queryColumnList.add(AntlrUtil.aliasStatement(AntlrUtil.listColumnStatement(".", new SymbolStatement.SingleMarkStatement(alias), new SymbolStatement.SingleMarkStatement(columnInfo.getColumn())), new SymbolStatement.SingleMarkStatement(columnInfo.getName())));
                                        break;
                                    }
                                }
                            } else {
                                ColumnInfo columnInfo = rootTableInfo.getColumnInfo(fieldName);
                                if (columnInfo != null) {
                                    queryColumnList.add(AntlrUtil.aliasStatement(AntlrUtil.listColumnStatement(".", new SymbolStatement.SingleMarkStatement(alias), new SymbolStatement.SingleMarkStatement(columnInfo.getColumn())), new SymbolStatement.SingleMarkStatement(columnInfo.getName())));
                                }
                            }
                        } else {
                            getQueryFromBean(tableFactory, fieldTable, ReflectUtil.getColType(colType, field), tableScanInfoMap, queryColumnList);
                        }
                    } else {
                        ColumnExpr columnExpr = new ColumnExpr(new ExprReader(value.value()), null);
                        queryColumnList.add(AntlrUtil.aliasStatement(columnExpr.expr(), new SymbolStatement.SingleMarkStatement(field.getName())));
                    }
                }
            }
        }
    }

    protected String getTableName(Class<?> type) {
        return SystemUtil.getTableName(type);
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}
