package com.moxa.dream.mate.block.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.block.invoker.ColumnBlockInvoker;

import java.util.List;

public class ColumnBlockHandler extends AbstractHandler {
    private ColumnBlockInvoker columnBlockInvoker;

    public ColumnBlockHandler(ColumnBlockInvoker columnBlockInvoker) {
        this.columnBlockInvoker = columnBlockInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        SymbolStatement.LetterStatement letterStatement = (SymbolStatement.LetterStatement) statement;
        String column = letterStatement.getValue();
        if (columnBlockInvoker.filter(column)) {
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
