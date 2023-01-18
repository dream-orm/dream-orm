package com.moxa.dream.mate.tenant.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.mate.tenant.invoker.TenantInvoker;
import com.moxa.dream.system.antlr.invoker.MarkInvoker;

import java.util.List;

public class TenantInsertHandler extends AbstractHandler {
    private TenantInvoker tenantInvoker;

    public TenantInsertHandler(TenantInvoker tenantInvoker) {
        this.tenantInvoker = tenantInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        InsertStatement insertStatement = (InsertStatement) statement;
        Statement tableStatement = insertStatement.getTable();
        String table = ((SymbolStatement) tableStatement).getValue();
        if (tenantInvoker.isTenant(table)) {
            Statement params = insertStatement.getParams();
            Statement values = insertStatement.getValues();
            if (params instanceof BraceStatement && values instanceof InsertStatement.ValuesStatement) {
                BraceStatement braceStatement = (BraceStatement) params;
                ListColumnStatement paramListStatement = (ListColumnStatement) braceStatement.getStatement();
                Statement[] paramColumnList = paramListStatement.getColumnList();
                InsertStatement.ValuesStatement valuesStatement = (InsertStatement.ValuesStatement) values;
                BraceStatement valuesBraceStatement = (BraceStatement) valuesStatement.getStatement();
                ListColumnStatement valuesListStatement = (ListColumnStatement) valuesBraceStatement.getStatement();
                Statement[] valuesColumnList = valuesListStatement.getColumnList();
                int i = 0;
                String tenantColumn = tenantInvoker.getTenantColumn();
                for (; i < paramColumnList.length; i++) {
                    SymbolStatement symbolStatement = (SymbolStatement) paramColumnList[i];
                    String column = symbolStatement.getValue();
                    if (tenantColumn.equalsIgnoreCase(column)) {
                        break;
                    }
                }
                InvokerStatement invokerStatement = AntlrUtil.invokerStatement(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn));
                if (i < paramColumnList.length) {
                    valuesColumnList[i] = invokerStatement;
                } else {
                    paramListStatement.add(new SymbolStatement.LetterStatement(tenantColumn));
                    valuesListStatement.add(invokerStatement);
                }
            }
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof InsertStatement;
    }
}
