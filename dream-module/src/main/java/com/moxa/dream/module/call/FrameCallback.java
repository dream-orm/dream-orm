package com.moxa.dream.module.call;

import com.moxa.dream.antlr.callback.Callback;
import com.moxa.dream.antlr.expr.InsertExpr;
import com.moxa.dream.antlr.expr.QueryExpr;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.table.ColumnInfo;
import com.moxa.dream.module.table.TableInfo;
import com.moxa.dream.module.table.factory.TableFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class FrameCallback implements Callback {
    @Override
    public Statement call(String className, String methodName, String[] params, Object[] args) {
        return null;
    }

    public Statement selectById(MethodInfo methodInfo, String param) {
        Class colType = methodInfo.getColType();
        Configuration configuration = methodInfo.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        TableInfo tableInfo = tableFactory.getTableInfo(colType);
        String table = tableInfo.getTable();
        ColumnInfo primary = tableInfo.getPrimary();
        String columns = tableInfo.getColumnInfoList().stream()
                .map(columnInfo -> table + "." + columnInfo.getColumn())
                .collect(Collectors.joining(","));
        String sql = "select " + columns + " from " + table + " where " + table + "." + primary.getColumn() + "="
                + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", param + "." + primary.getName());
        Statement statement = new QueryExpr(new ExprReader(sql)).expr();
        return statement;
    }

    public Statement updateById(MethodInfo methodInfo, String param) {
        Class colType = methodInfo.getColType();
        Configuration configuration = methodInfo.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        TableInfo tableInfo = tableFactory.getTableInfo(colType);
        String table = tableInfo.getTable();
        ColumnInfo primary = tableInfo.getPrimary();
        Collection<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();
        List<String> updateList = new ArrayList<>();
        for (ColumnInfo columnInfo : columnInfoList) {
            String column = columnInfo.getColumn();
            String name = columnInfo.getName();
            updateList.add(column + "=" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", param + "." + name));
        }
        String sql = "update " + table + " set " + String.join(",", updateList) + " where " + table + "." + primary.getColumn()
                + "=" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", param + "." + primary.getName());
        InsertExpr insertExpr = new InsertExpr(new ExprReader(sql));
        Statement statement = insertExpr.expr();
        return statement;
    }

    public Statement insert(MethodInfo methodInfo, String param) {
        Class colType = methodInfo.getColType();
        Configuration configuration = methodInfo.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        TableInfo tableInfo = tableFactory.getTableInfo(colType);
        String table = tableInfo.getTable();
        Collection<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();
        List<String> columnList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        for (ColumnInfo columnInfo : columnInfoList) {
            String column = columnInfo.getColumn();
            String name = columnInfo.getName();
            columnList.add(column);
            valueList.add(InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", param + "." + name));
        }
        String sql = "insert into " + table + "(" + String.join(",", columnList) + ")values(" + String.join(",", valueList) + ")";
        InsertExpr insertExpr = new InsertExpr(new ExprReader(sql));
        Statement statement = insertExpr.expr();
        return statement;
    }
}
