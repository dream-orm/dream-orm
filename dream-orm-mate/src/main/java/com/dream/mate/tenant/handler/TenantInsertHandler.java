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

import java.util.List;

public class TenantInsertHandler extends AbstractHandler {
    private TenantInjectInvoker tenantInjectInvoker;

    public TenantInsertHandler(TenantInjectInvoker tenantInjectInvoker) {
        this.tenantInjectInvoker = tenantInjectInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        InsertStatement insertStatement = (InsertStatement) statement;
        Statement tableStatement = insertStatement.getTable();
        String table = ((SymbolStatement) tableStatement).getValue();
        if (tenantInjectInvoker.isTenant(table)) {
            Statement columns = insertStatement.getColumns();
            Statement values = insertStatement.getValues();
            if (columns instanceof BraceStatement && values instanceof InsertStatement.ValuesStatement) {
                BraceStatement braceStatement = (BraceStatement) columns;
                ListColumnStatement paramListStatement = (ListColumnStatement) braceStatement.getStatement();
                Statement[] paramColumnList = paramListStatement.getColumnList();
                InsertStatement.ValuesStatement valuesStatement = (InsertStatement.ValuesStatement) values;
                BraceStatement valuesBraceStatement = (BraceStatement) valuesStatement.getStatement();
                ListColumnStatement valuesListStatement = (ListColumnStatement) valuesBraceStatement.getStatement();
                Statement[] valuesColumnList = valuesListStatement.getColumnList();
                int i = 0;
                String tenantColumn = tenantInjectInvoker.getTenantColumn(table);
                for (; i < paramColumnList.length; i++) {
                    SymbolStatement symbolStatement = (SymbolStatement) paramColumnList[i];
                    String column = symbolStatement.getValue();
                    if (tenantColumn.equalsIgnoreCase(column)) {
                        break;
                    }
                }
                InvokerStatement invokerStatement = AntlrUtil.invokerStatement(TenantGetInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, new SymbolStatement.LetterStatement(tenantColumn));
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
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof InsertStatement;
    }
}
