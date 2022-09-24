package com.moxa.dream.mate.filter.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.filter.invoker.ColumnFilterInvoker;

import java.util.List;

public class ColumnFilterHandler extends AbstractHandler {
    private ColumnFilterInvoker columnFilterInvoker;

    public ColumnFilterHandler(ColumnFilterInvoker columnFilterInvoker) {
        this.columnFilterInvoker = columnFilterInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        SymbolStatement.LetterStatement letterStatement = (SymbolStatement.LetterStatement) statement;
        String column = letterStatement.getValue();
        if (columnFilterInvoker.filter(column)) {
            SymbolStatement.SingleMarkStatement singleMarkStatement = new SymbolStatement.SingleMarkStatement(column);
            letterStatement.replaceWith(singleMarkStatement);
            return singleMarkStatement;
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof SymbolStatement.LetterStatement;
    }
}
