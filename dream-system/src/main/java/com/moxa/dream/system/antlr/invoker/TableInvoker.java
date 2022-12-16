package com.moxa.dream.system.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.expr.FromExpr;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToNativeSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.table.JoinInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.util.common.LowHashMap;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public class TableInvoker extends AbstractInvoker {

    public static final String FUNCTION = "table";

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (ObjectUtil.isNull(columnList)) {
            throw new AntlrException("@table要求参数至少一个");
        }
        LowHashMap<List<String>> tableSQLMap = new LowHashMap<>();
        String[] tableList = new String[columnList.length];
        for (int i = 0; i < columnList.length; i++) {
            if (columnList[i] instanceof SymbolStatement.LetterStatement) {
                String table = ((SymbolStatement.LetterStatement) columnList[i]).getValue();
                tableList[i] = table;
                tableSQLMap.put(table, null);
            } else {
                throw new AntlrException("@table参数类型不合法，不合法参数：'" + new ToNativeSQL().toStr(columnList[i], null, null) + "'");
            }
        }
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        Configuration configuration = methodInfo.getConfiguration();
        TableFactory tableFactory = configuration.getTableFactory();
        for (String table : tableList) {
            TableInfo tableInfo = tableFactory.getTableInfo(table);
            ObjectUtil.requireNonNull(tableInfo, "表'" + table + "'没有生成对应TableInfo");
            for (String joinTable : tableList) {
                if (!table.equalsIgnoreCase(joinTable)) {
                    JoinInfo joinInfo = tableInfo.getJoinInfo(joinTable);
                    if (joinInfo != null) {
                        String joinSQL = joinInfo.getJoinType().getJoinType() +
                                " " +
                                joinInfo.getJoinTable() +
                                " ON " + joinInfo.getTable() + "." + joinInfo.getColumn() + "=" +
                                joinInfo.getJoinTable() + "." + joinInfo.getJoinColumn();
                        List<String> tableSQLList = tableSQLMap.get(table);
                        if (tableSQLList == null) {
                            tableSQLList = new ArrayList<>();
                            tableSQLMap.put(table, tableSQLList);
                        }
                        tableSQLList.add(joinSQL);
                        List<String> removeTableSQLList = tableSQLMap.remove(joinTable);
                        if (!ObjectUtil.isNull(removeTableSQLList)) {
                            tableSQLList.addAll(removeTableSQLList);
                        }
                    }
                }
            }
        }
        if (tableSQLMap.size() != 1) {
            throw new AntlrException("表:" + tableSQLMap.keySet() + "没有建立关联关系");
        } else {
            Statement parentStatement = invokerStatement.getParentStatement();
            if (!(parentStatement instanceof FromStatement)) {
                throw new AntlrException("@table只能跟在from后面");
            }
            FromStatement fromStatement = (FromStatement) parentStatement;
            String mainTable = tableSQLMap.keySet().toArray(new String[0])[0];
            SymbolStatement symbolStatement = new SymbolStatement.LetterStatement(mainTable);
            invokerStatement.replaceWith(symbolStatement);
            List<String> list = tableSQLMap.get(mainTable);
            String joinSQL = "";
            if (!ObjectUtil.isNull(list)) {
                joinSQL = String.join(" ", tableSQLMap.get(mainTable));
            }
            QueryStatement queryStatement = (QueryStatement) fromStatement.getParentStatement();
            FromExpr fromExpr = new FromExpr(new ExprReader("from " + mainTable + " " + joinSQL));
            FromStatement statement = (FromStatement) fromExpr.expr();
            queryStatement.setFromStatement(statement);
            return toSQL.toStr(statement, assist, invokerList).substring(5);
        }
    }

    @Override
    public Invoker newInstance() {
        return this;
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}
