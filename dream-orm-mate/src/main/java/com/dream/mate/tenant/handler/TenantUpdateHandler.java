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

import java.util.ArrayList;
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
            ListColumnStatement conditionList = (ListColumnStatement) updateStatement.getConditionList();
            Statement[] columnList = conditionList.getColumnList();
            List<Statement> statementList = new ArrayList<>();
            for (int i = 0; i < columnList.length; i++) {
                ConditionStatement conditionStatement = (ConditionStatement) columnList[i];
                Statement columnStatement = conditionStatement.getLeft();
                if (columnStatement instanceof SymbolStatement) {
                    String column = ((SymbolStatement) columnStatement).getValue();
                    if (!tenantInjectInvoker.getTenantColumn().equalsIgnoreCase(column)) {
                        statementList.add(conditionStatement);
                    }
                }
            }
            if (statementList.size() != columnList.length) {
                conditionList.setColumnList(statementList.toArray(new Statement[statementList.size()]));
            }
            String tenantColumn = tenantInjectInvoker.getTenantColumn();
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.LetterStatement(tenantColumn));
            conditionStatement.setOper(new OperStatement.EQStatement());
            conditionStatement.setRight(AntlrUtil.invokerStatement(TenantGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn)));
            WhereStatement whereStatement = (WhereStatement) updateStatement.getWhere();
            if (whereStatement == null) {
                whereStatement = new WhereStatement();
                updateStatement.setWhere(whereStatement);
                whereStatement.setStatement(conditionStatement);
                updateStatement.setWhere(whereStatement);
            } else {
                MateUtil.appendWhere(whereStatement, conditionStatement);
            }
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof UpdateStatement;
    }
}
