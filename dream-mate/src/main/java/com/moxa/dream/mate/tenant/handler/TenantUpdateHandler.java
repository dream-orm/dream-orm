package com.moxa.dream.mate.tenant.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.tenant.invoker.TenantGetInvoker;
import com.moxa.dream.mate.tenant.invoker.TenantInjectInvoker;
import com.moxa.dream.mate.util.MateUtil;

import java.util.List;

public class TenantUpdateHandler extends AbstractHandler {
    private TenantInjectInvoker tenantInjectInvoker;

    public TenantUpdateHandler(TenantInjectInvoker tenantInjectInvoker) {
        this.tenantInjectInvoker = tenantInjectInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        UpdateStatement updateStatement = (UpdateStatement) statement;
        Statement tableStatement = updateStatement.getTable();
        SymbolStatement symbolStatement = (SymbolStatement) tableStatement;
        String table = symbolStatement.getValue();
        if (tenantInjectInvoker.isTenant(table)) {
            String tenantColumn = tenantInjectInvoker.getTenantColumn();
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.LetterStatement(tenantColumn));
            conditionStatement.setOper(new OperStatement.EQStatement());
            conditionStatement.setRight(AntlrUtil.invokerStatement(TenantGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn)));
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
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
            ConditionStatement conditionStatement = (ConditionStatement) statement;
            Statement columnStatement = conditionStatement.getLeft();
            if (columnStatement instanceof SymbolStatement) {
                String column = ((SymbolStatement) columnStatement).getValue();
                if (tenantInjectInvoker.getTenantColumn().equalsIgnoreCase(column)) {
                    return null;
                }
            }
            return statement;
        }

        @Override
        protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
            access = true;
            return super.handlerAfter(statement, assist, sql, life);
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
