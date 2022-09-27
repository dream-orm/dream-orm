package com.moxa.dream.mate.tenant.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.tenant.invoker.TenantInvoker;
import com.moxa.dream.mate.util.MateUtil;

import java.util.ArrayList;
import java.util.List;

public class TenantUpdateHandler extends AbstractHandler {
    private TenantInvoker tenantInvoker;

    public TenantUpdateHandler(TenantInvoker tenantInvoker) {
        this.tenantInvoker = tenantInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        UpdateStatement updateStatement = (UpdateStatement) statement;
        Statement tableStatement = updateStatement.getTable();
        SymbolStatement symbolStatement = (SymbolStatement) tableStatement;
        String table = symbolStatement.getValue();
        if (tenantInvoker.isTenant(table)) {
            String tenantColumn = tenantInvoker.getTenantColumn();
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.LetterStatement(tenantColumn));
            conditionStatement.setOper(new OperStatement.EQStatement());
            conditionStatement.setRight(InvokerUtil.wrapperInvoker(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", new SymbolStatement.LetterStatement(tenantColumn)));
            WhereStatement whereStatement = (WhereStatement) updateStatement.getWhere();
            if (whereStatement == null) {
                whereStatement = new WhereStatement();
                updateStatement.setWhere(whereStatement);
                whereStatement.setCondition(conditionStatement);
                updateStatement.setWhere(whereStatement);
            } else {
                MateUtil.appendWhere(whereStatement, conditionStatement);
            }
        }
        return statement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{new ConditionHandler()};
    }


    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof UpdateStatement;
    }

    class ConditionHandler extends AbstractHandler {
        private boolean access = true;

        @Override
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            ConditionStatement conditionStatement = (ConditionStatement) statement;
            Statement columnStatement = conditionStatement.getLeft();
            if (columnStatement instanceof SymbolStatement) {
                String column = ((SymbolStatement) columnStatement).getValue();
                if (tenantInvoker.getTenantColumn().equalsIgnoreCase(column)) {
                    return null;
                }
            }
            return statement;
        }

        @Override
        protected String handlerAfter(Assist assist, String sql, int life) throws InvokerException {
            access=true;
            return super.handlerAfter(assist, sql, life);
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            if (statement instanceof WhereStatement) {
                access = false;
            }
            return access && statement instanceof ConditionStatement;
        }
    }
}
