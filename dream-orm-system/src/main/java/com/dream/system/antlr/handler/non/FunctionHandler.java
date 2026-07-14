package com.dream.system.antlr.handler.non;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.FunctionStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public class FunctionHandler extends AbstractHandler {
    private static final NullFlag NULL_FLAG = new NullFlag();

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        assist.setCustom(NullFlag.class, null);
        return statement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{new ParamListHandler()};
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof FunctionStatement;
    }

    @Override
    protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        if (assist.getCustom(NullFlag.class) != null) {
            assist.setCustom(NullFlag.class, null);
            return "";
        } else {
            return super.handlerAfter(statement, assist, sql, life);
        }
    }

    public static class ParamListHandler extends AbstractHandler {

        @Override
        protected String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
            if ("".equals(sql)) {
                assist.setCustom(NullFlag.class, NULL_FLAG);
                return "";
            }
            return sql;
        }

        @Override
        protected boolean interest(Statement statement, Assist assist) {
            return true;
        }
    }

    private static class NullFlag {

    }
}
