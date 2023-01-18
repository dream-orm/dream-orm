package com.moxa.dream.mate.tenant.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.expr.SymbolExpr;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.tenant.invoker.TenantInvoker;
import com.moxa.dream.mate.util.MateUtil;
import com.moxa.dream.system.antlr.handler.scan.QueryScanHandler;
import com.moxa.dream.system.antlr.invoker.MarkInvoker;
import com.moxa.dream.system.antlr.invoker.ScanInvoker;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class TenantQueryHandler extends AbstractHandler {
    private TenantInvoker tenantInvoker;
    private Deque<QueryStatement> queryDeque = new ArrayDeque<>();

    public TenantQueryHandler(TenantInvoker tenantInvoker) {
        this.tenantInvoker = tenantInvoker;

    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        queryDeque.push((QueryStatement) statement);
        return super.handlerBefore(statement, assist, toSQL, invokerList, life);
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{new TenantFromHandler()};
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        queryDeque.poll();
        return super.handlerAfter(statement, assist, sql, life);
    }

    class TenantFromHandler extends AbstractHandler {

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
            FromStatement fromStatement = (FromStatement) statement;
            ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(fromStatement.getMainTable(), true);
            if (tableScanInfo != null) {
                String table = tableScanInfo.getTable();
                if (tenantInvoker.isTenant(table)) {
                    String tenantColumn = tenantInvoker.getTenantColumn();
                    ConditionStatement conditionStatement = new ConditionStatement();
                    conditionStatement.setLeft(new SymbolExpr(new ExprReader(tableScanInfo.getAlias() + "." + tenantColumn)).expr());
                    conditionStatement.setOper(new OperStatement.EQStatement());
                    conditionStatement.setRight(AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn)));
                    QueryStatement queryStatement = queryDeque.peek();
                    WhereStatement whereStatement = queryStatement.getWhereStatement();
                    if (whereStatement == null) {
                        whereStatement = new WhereStatement();
                        whereStatement.setCondition(conditionStatement);
                        queryStatement.setWhereStatement(whereStatement);
                    } else {
                        MateUtil.appendWhere(whereStatement, conditionStatement);
                    }
                }
            }
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof FromStatement;
        }

        @Override
        protected Handler[] handlerBound() {
            return new Handler[]{new JoinHandler()};
        }

        class JoinHandler extends AbstractHandler {
            @Override
            protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
                JoinStatement joinStatement = (JoinStatement) statement;
                ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(joinStatement.getJoinTable(), false);
                if (tableScanInfo != null) {
                    String table = tableScanInfo.getTable();
                    if (tenantInvoker.isTenant(table)) {
                        String tenantColumn = tenantInvoker.getTenantColumn();
                        ConditionStatement conditionStatement = new ConditionStatement();
                        conditionStatement.setLeft(new SymbolExpr(new ExprReader(tableScanInfo.getAlias() + "." + tenantColumn)).expr());
                        conditionStatement.setOper(new OperStatement.EQStatement());
                        conditionStatement.setRight(AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn)));
                        Statement joinOnStatement = joinStatement.getOn();
                        BraceStatement braceStatement = new BraceStatement(joinOnStatement);
                        ConditionStatement joinConditionStatement = new ConditionStatement();
                        joinConditionStatement.setLeft(braceStatement);
                        joinConditionStatement.setOper(new OperStatement.ANDStatement());
                        joinConditionStatement.setRight(conditionStatement);
                        joinStatement.setOn(joinConditionStatement);
                    }
                }
                return statement;
            }

            @Override
            protected boolean interest(Statement statement, Assist sqlAssist) {
                return statement instanceof JoinStatement;
            }
        }
    }
}
