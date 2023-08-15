package com.dream.mate.transform.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.transform.invoker.TransformInvoker;

import java.util.List;

public class LetterHandler extends AbstractHandler {
    private TransformInvoker transformInvoker;

    public LetterHandler(TransformInvoker transformInvoker) {
        this.transformInvoker = transformInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        SymbolStatement.LetterStatement letterStatement = (SymbolStatement.LetterStatement) statement;
        String column = letterStatement.getValue();
        if (transformInvoker.intercept(column,invokerList)) {
            SymbolStatement.SingleMarkStatement singleMarkStatement = new SymbolStatement.SingleMarkStatement(column);
            letterStatement.replaceWith(singleMarkStatement);
            return singleMarkStatement;
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof SymbolStatement.LetterStatement;
    }
}
