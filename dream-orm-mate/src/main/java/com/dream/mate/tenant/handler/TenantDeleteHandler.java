package com.dream.mate.tenant.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.tenant.invoker.TenantGetInvoker;
import com.dream.mate.tenant.invoker.TenantInjectInvoker;
import com.dream.mate.util.MateUtil;

import java.util.List;

public class TenantDeleteHandler extends AbstractHandler {
    public TenantInjectInvoker tenantInjectInvoker;

    public TenantDeleteHandler(TenantInjectInvoker tenantInjectInvoker) {
        this.tenantInjectInvoker = tenantInjectInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        DeleteStatement deleteStatement = (DeleteStatement) statement;
        Statement tableStatement = deleteStatement.getTable();
        SymbolStatement symbolStatement = (SymbolStatement) tableStatement;
        String table = symbolStatement.getValue();
        if (tenantInjectInvoker.isTenant(table)) {
            String tenantColumn = tenantInjectInvoker.getTenantColumn();
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.LetterStatement(tenantColumn));
            conditionStatement.setOper(new OperStatement.EQStatement());
            conditionStatement.setRight(AntlrUtil.invokerStatement(TenantGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn)));
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
