package com.moxa.dream.module.frame.callback;

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

import java.util.*;
import java.util.stream.Collectors;

public class UpdateByIdCallback {
    protected Map<Class, Statement> statementMap = new HashMap<>();

    public Statement call(MethodInfo methodInfo, Object arg) {
        Class<?> type = arg.getClass();
        Statement statement = statementMap.get(type);
        if (statement == null) {
            synchronized (statementMap) {
                statement = statementMap.get(type);
                if (statement == null) {
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
                        updateList.add(column + "=" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",",  name));
                    }
                    String sql = "update " + table + " set " + String.join(",", updateList) + " where " + table + "." + primary.getColumn()
                            + "=" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",",  primary.getName());
                    InsertExpr insertExpr = new InsertExpr(new ExprReader(sql));
                    statement = insertExpr.expr();
                    statementMap.put(type, statement);
                }
            }
        }
        return statement;
    }
}
