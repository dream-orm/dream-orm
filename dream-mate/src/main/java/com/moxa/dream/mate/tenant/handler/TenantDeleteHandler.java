package com.moxa.dream.mate.tenant.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.tenant.invoker.TenantInvoker;
import com.moxa.dream.mate.util.MateUtil;
import com.moxa.dream.system.antlr.invoker.$Invoker;

import java.util.List;

public class TenantDeleteHandler extends AbstractHandler {
    public TenantInvoker tenantInvoker;

    public TenantDeleteHandler(TenantInvoker tenantInvoker) {
        this.tenantInvoker = tenantInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        DeleteStatement deleteStatement = (DeleteStatement) statement;
        Statement tableStatement = deleteStatement.getTable();
        SymbolStatement symbolStatement = (SymbolStatement) tableStatement;
        String table = symbolStatement.getValue();
        if (tenantInvoker.isTenant(table)) {
            String tenantColumn = tenantInvoker.getTenantColumn();
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.LetterStatement(tenantColumn));
            conditionStatement.setOper(new OperStatement.EQStatement());
            conditionStatement.setRight(AntlrUtil.invokerStatement($Invoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn)));
            WhereStatement whereStatement = (WhereStatement) deleteStatement.getWhere();
            if (whereStatement == null) {
                whereStatement = new WhereStatement();
                deleteStatement.setWhere(whereStatement);
                whereStatement.setCondition(conditionStatement);
                deleteStatement.setWhere(whereStatement);
            } else {
                MateUtil.appendWhere(whereStatement, conditionStatement);
            }
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof DeleteStatement;
    }
}
