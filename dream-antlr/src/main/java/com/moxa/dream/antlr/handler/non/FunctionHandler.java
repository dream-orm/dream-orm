package com.moxa.dream.antlr.handler.non;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.FunctionStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.ArrayList;
import java.util.List;

public class FunctionHandler extends AbstractHandler {
    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        FunctionStatement functionStatement = (FunctionStatement) statement;
        ListColumnStatement paramsStatement = (ListColumnStatement) functionStatement.getParamsStatement();
        Statement[] columnList = paramsStatement.getColumnList();
        SymbolStatement.LetterStatement cut = paramsStatement.getCut();
        List<String> sqlList = new ArrayList<>();
        for (Statement columnStatement : columnList) {
            String sql = toSQL.toStr(columnStatement, assist, invokerList);
            if (!"".equals(sql)) {
                sqlList.add(sql);
            } else {
                return null;
            }
        }
        return new SymbolStatement.LetterStatement("(" + String.join(cut.getSymbol(), sqlList) + ")");

    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof FunctionStatement;
    }
}
