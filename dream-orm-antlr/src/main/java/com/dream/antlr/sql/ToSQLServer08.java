package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;

import java.util.List;

/**
 * sqlserver2008方言
 */
public class ToSQLServer08 extends ToSQLServer {
    @Override
    protected String toString(QueryStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        LimitStatement limitStatement = statement.getLimitStatement();
        if (limitStatement != null) {
            Statement first;
            Statement second;
            if (limitStatement.isOffset()) {
                first = limitStatement.getSecond();
                second = limitStatement.getFirst();
            } else {
                first = limitStatement.getFirst();
                second = limitStatement.getSecond();
            }
            statement.setLimitStatement(null);
            OrderStatement orderStatement = statement.getOrderStatement();
            if (orderStatement == null) {
                orderStatement = new OrderStatement();
                orderStatement.setStatement(new SymbolStatement.LetterStatement("(select 0)"));
            }
            statement.setOrderStatement(null);
            RowNumberStatement rowNumberStatement = new RowNumberStatement();
            RowNumberStatement.OverStatement overStatement = new RowNumberStatement.OverStatement();
            overStatement.setOrderStatement(orderStatement);
            rowNumberStatement.setStatement(overStatement);
            AliasStatement aliasStatement = AntlrUtil.aliasStatement(rowNumberStatement, new SymbolStatement.LetterStatement("V_R_N"));
            statement.getSelectStatement().getSelectList().add(aliasStatement);
            ListColumnStatement listColumnStatement = AntlrUtil.listColumnStatement(",", "*");
            ConditionStatement conditionStatement;
            if (second != null) {
                conditionStatement = AntlrUtil.conditionStatement(AntlrUtil.conditionStatement(new SymbolStatement.LetterStatement("V_R_N"), new OperStatement.LEQStatement(), second), new OperStatement.ANDStatement(), AntlrUtil.conditionStatement(new SymbolStatement.LetterStatement("V_R_N"), new OperStatement.GTStatement(), first));
            } else {
                conditionStatement = AntlrUtil.conditionStatement(new SymbolStatement.LetterStatement("V_R_N"), new OperStatement.LEQStatement(), first);
            }
            statement = AntlrUtil.queryStatement(listColumnStatement, AntlrUtil.aliasStatement(new BraceStatement(statement), new SymbolStatement.LetterStatement("V_T")), conditionStatement);
        }
        return super.toString(statement, assist, invokerList);
    }
}
