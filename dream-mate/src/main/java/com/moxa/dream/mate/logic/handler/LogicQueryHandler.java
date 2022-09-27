package com.moxa.dream.mate.logic.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.SymbolExpr;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.scan.QueryScanHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.ScanInvoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.logic.invoker.LogicInvoker;
import com.moxa.dream.mate.util.MateUtil;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class LogicQueryHandler extends AbstractHandler {
    private LogicInvoker logicInvoker;
    private Deque<QueryStatement> queryDeque = new ArrayDeque<>();

    public LogicQueryHandler(LogicInvoker logicInvoker) {
        this.logicInvoker = logicInvoker;

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
        return new Handler[]{new TenantFromHandler()};
    }

    @Override
    protected String handlerAfter(Assist assist, String sql, int life) throws InvokerException {
        queryDeque.poll();
        return sql;
    }

    class TenantFromHandler extends AbstractHandler {

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            FromStatement fromStatement = (FromStatement) statement;
            ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(assist, toSQL, invokerList, fromStatement.getMainTable(), true);
            if (tableScanInfo != null) {
                String table = tableScanInfo.getTable();
                if (logicInvoker.isLogicDelete(table)) {
                    String logicColumn = logicInvoker.getLogicColumn();
                    ConditionStatement conditionStatement = new ConditionStatement();
                    conditionStatement.setLeft(new SymbolExpr(new ExprReader(tableScanInfo.getAlias() + "." + logicColumn)).expr());
                    conditionStatement.setOper(new OperStatement.EQStatement());
                    conditionStatement.setRight(new SymbolStatement.LetterStatement(logicInvoker.getNegativeValue()));
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
            protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
                JoinStatement joinStatement = (JoinStatement) statement;
                ScanInvoker.TableScanInfo tableScanInfo = new QueryScanHandler(null).getTableScanInfo(assist, toSQL, invokerList, joinStatement.getJoinTable(), false);
                if (tableScanInfo != null) {
                    String table = tableScanInfo.getTable();
                    if (logicInvoker.isLogicDelete(table)) {
                        String logicColumn = logicInvoker.getLogicColumn();
                        ConditionStatement conditionStatement = new ConditionStatement();
                        conditionStatement.setLeft(new SymbolExpr(new ExprReader(tableScanInfo.getAlias() + "." + logicColumn)).expr());
                        conditionStatement.setOper(new OperStatement.EQStatement());
                        conditionStatement.setRight(new SymbolStatement.LetterStatement(logicInvoker.getNegativeValue()));
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
