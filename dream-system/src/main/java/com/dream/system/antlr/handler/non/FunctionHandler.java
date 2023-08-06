package com.dream.system.antlr.handler.non;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.FunctionStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.util.common.ObjectUtil;

import java.util.List;

public class FunctionHandler extends AbstractHandler {

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        return statement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{new ParamListHandler()};
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
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
        protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
            ListColumnStatement listColumnStatement = (ListColumnStatement) statement;
            Statement[] columnList = listColumnStatement.getColumnList();
            if (!ObjectUtil.isNull(columnList)) {
                String cut = toSQL.toStr(listColumnStatement.getCut(), assist, invokerList);
                ListColumnStatement statementList = new ListColumnStatement(cut);
                for (int i = 0; i < columnList.length; i++) {
                    String column = toSQL.toStr(columnList[i], assist, invokerList);
                    if ("".equals(column)) {
                        assist.setCustom(NullFlag.class, new NullFlag());
                        return null;
                    }
                    statementList.add(new SymbolStatement.LetterStatement(column));
                }
                return statementList;
            }
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, Assist sqlAssist) {
            return statement instanceof ListColumnStatement;
        }
    }

    private static class NullFlag {

    }
}
