package com.moxa.dream.mate.permission.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.permission.invoker.PermissionGetInvoker;
import com.moxa.dream.mate.permission.invoker.PermissionInjectInvoker;
import com.moxa.dream.mate.util.MateUtil;
import com.moxa.dream.system.antlr.handler.scan.QueryScanHandler;
import com.moxa.dream.system.antlr.invoker.ScanInvoker;

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
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
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
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        queryDeque.poll();
        return sql;
    }

    class PermissionFromHandler extends AbstractHandler {

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
            FromStatement fromStatement = (FromStatement) statement;
            ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(fromStatement.getMainTable(), true);
            if (tableScanInfo != null) {
                String table = tableScanInfo.getTable();
                if (permissionInjectInvoker.isPermissionInject(table, life)) {
                    QueryStatement queryStatement = queryDeque.peek();
                    WhereStatement whereStatement = queryStatement.getWhereStatement();
                    InvokerStatement invokerStatement = AntlrUtil.invokerStatement(
                            PermissionGetInvoker.FUNCTION,
                            Invoker.DEFAULT_NAMESPACE,
                            new SymbolStatement.LetterStatement(tableScanInfo.getTable()),
                            new SymbolStatement.LetterStatement(tableScanInfo.getAlias()));
                    if (whereStatement == null) {
                        whereStatement = new WhereStatement();
                        whereStatement.setCondition(invokerStatement);
                        queryStatement.setWhereStatement(whereStatement);
                    } else {
                        MateUtil.appendWhere(whereStatement, invokerStatement);
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
