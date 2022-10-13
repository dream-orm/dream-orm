package com.moxa.dream.mate.permission.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.scan.QueryScanHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.permission.invoker.PermissionGetInvoker;
import com.moxa.dream.mate.permission.invoker.PermissionInjectInvoker;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class PermissionQueryHandler extends AbstractHandler {
    private PermissionInjectInvoker permissionInjectInvoker;
    private Deque<QueryStatement> queryDeque = new ArrayDeque<>();

    public PermissionQueryHandler(PermissionInjectInvoker permissionInjectInvoker) {
        this.permissionInjectInvoker = permissionInjectInvoker;

    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        queryDeque.push((QueryStatement) statement);
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{new PermissionFromHandler()};
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws InvokerException {
        queryDeque.poll();
        return sql;
    }

    class PermissionFromHandler extends AbstractHandler {

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            FromStatement fromStatement = (FromStatement) statement;
            ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(fromStatement.getMainTable(), true);
            if (tableScanInfo != null) {
                String table = tableScanInfo.getTable();
                if (permissionInjectInvoker.isPermissionInject(table, life)) {
                    QueryStatement queryStatement = queryDeque.peek();
                    WhereStatement whereStatement = queryStatement.getWhereStatement();
                    InvokerStatement invokerStatement = InvokerUtil.wrapperInvoker(null,
                            PermissionGetInvoker.getName(),
                            ",",
                            new SymbolStatement.LetterStatement(tableScanInfo.getTable()),
                            new SymbolStatement.LetterStatement(tableScanInfo.getAlias()));
                    if (whereStatement == null) {
                        whereStatement = new WhereStatement();
                        whereStatement.setCondition(invokerStatement);
                        queryStatement.setWhereStatement(whereStatement);
                    } else {
                        Statement condition = whereStatement.getCondition();
                        BraceStatement braceStatement = new BraceStatement(condition);
                        ConditionStatement conditionStatement = new ConditionStatement();
                        conditionStatement.setLeft(braceStatement);
                        conditionStatement.setOper(new OperStatement.ANDStatement());
                        conditionStatement.setRight(invokerStatement);
                        whereStatement.setCondition(conditionStatement);
                    }
                }
            }
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof FromStatement;
        }
    }
}
