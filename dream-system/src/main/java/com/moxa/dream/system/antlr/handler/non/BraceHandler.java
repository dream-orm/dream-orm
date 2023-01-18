package com.moxa.dream.system.antlr.handler.non;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.ExprUtil;

import java.util.List;

public class BraceHandler extends AbstractHandler {

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        BraceStatement braceStatement = (BraceStatement) statement;
        String val = toSQL.toStr(braceStatement.getStatement(), assist, invokerList);
        if (ExprUtil.isEmpty(val)) {
            return null;
        }
        return new SymbolStatement.LetterStatement("(" + val + ")");
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof BraceStatement;
    }
}
