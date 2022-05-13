package com.moxa.dream.antlr.handler.non;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.FunctionStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.List;

public class FunctionHandler extends AbstractHandler {
    private boolean returnNull = false;

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        return statement;
    }

    @Override
    protected Handler[] handlerBound() {
        return new Handler[]{new ParamListHandler(this)};
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof FunctionStatement;
    }

    @Override
    protected String handlerAfter(ToAssist assist, String sql, int life) throws InvokerException {
        if (returnNull) {
            return "";
        } else {
            return super.handlerAfter(assist, sql, life);
        }
    }

    public static class ParamListHandler extends AbstractHandler {

        private FunctionHandler functionHandler;

        public ParamListHandler(FunctionHandler functionHandler) {
            this.functionHandler = functionHandler;
        }

        @Override
        protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
            ListColumnStatement listColumnStatement = (ListColumnStatement) statement;
            Statement[] columnList = listColumnStatement.getColumnList();
            if (!ObjectUtil.isNull(columnList)) {
                String cut = toSQL.toStr(listColumnStatement.getCut(), assist, invokerList);
                ListColumnStatement statementList = new ListColumnStatement(cut);
                for (int i = 0; i < columnList.length; i++) {
                    String column = toSQL.toStr(columnList[i], assist, invokerList);
                    if ("".equals(column)) {
                        functionHandler.returnNull = true;
                        return null;
                    }
                    statementList.add(new SymbolStatement.LetterStatement(column));
                }
                return statementList;
            }
            return statement;
        }

        @Override
        protected boolean interest(Statement statement, ToAssist sqlAssist) {
            return statement instanceof ListColumnStatement;
        }
    }
}
