package com.moxa.dream.mate.tenant.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.mate.tenant.invoker.TenantInvoker;

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
            removeTenantColumn(updateStatement);
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
                tenantInvoker.appendWhere(whereStatement, conditionStatement);
            }
        }
        return statement;
    }

    private void removeTenantColumn(UpdateStatement updateStatement) {
        ListColumnStatement conditionList = (ListColumnStatement) updateStatement.getConditionList();
        Statement[] columnList = conditionList.getColumnList();
        List<Statement> statementList = new ArrayList<>();
        for (Statement statement : columnList) {
            ConditionStatement conditionStatement = (ConditionStatement) statement;
            SymbolStatement leftStatement = (SymbolStatement) conditionStatement.getLeft();
            String column = leftStatement.getValue();
            if (!tenantInvoker.getTenantColumn().equalsIgnoreCase(column)) {
                statementList.add(conditionStatement);
            }
        }
        if (!statementList.isEmpty()) {
            ListColumnStatement listColumnStatement = new ListColumnStatement(conditionList.getCut().getSymbol());
            for (Statement statement : statementList) {
                listColumnStatement.add(statement);
            }
            updateStatement.setConditionList(listColumnStatement);
        }
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof UpdateStatement;
    }
}
