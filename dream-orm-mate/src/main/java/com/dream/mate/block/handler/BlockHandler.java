package com.dream.mate.block.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.block.invoker.BlockInvoker;

import java.util.List;

public class BlockHandler extends AbstractHandler {
    private BlockInvoker blockInvoker;

    public BlockHandler(BlockInvoker blockInvoker) {
        this.blockInvoker = blockInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        SymbolStatement.LetterStatement letterStatement = (SymbolStatement.LetterStatement) statement;
        String column = letterStatement.getValue();
        if (blockInvoker.filter(column)) {
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
