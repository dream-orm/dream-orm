package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;

import java.util.List;

/**
 * oracle11方言
 */
public class ToOracle11 extends ToPubSQL {
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
            ListColumnStatement selectList = statement.getSelectStatement().getSelectList();
            Statement[] columnList = selectList.getColumnList();
            Statement[] newColumnList = new Statement[columnList.length + 1];
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(new SymbolStatement.LetterStatement("ROWNUM"));
            aliasStatement.setAlias(new SymbolStatement.LetterStatement("rn"));
            newColumnList[0] = aliasStatement;
            System.arraycopy(columnList, 0, newColumnList, 1, columnList.length);
            selectList.setColumnList(newColumnList);

            QueryStatement queryStatement = new QueryStatement();
            SelectStatement selectStatement = new SelectStatement();
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            listColumnStatement.add(new SymbolStatement.LetterStatement("t_tmp.*"));
            selectStatement.setSelectList(listColumnStatement);
            queryStatement.setSelectStatement(selectStatement);
            AliasStatement tableAliasStatement = new AliasStatement();
            tableAliasStatement.setColumn(new BraceStatement(statement));
            tableAliasStatement.setAlias(new SymbolStatement.LetterStatement("t_tmp"));
            FromStatement fromStatement = new FromStatement();
            fromStatement.setMainTable(tableAliasStatement);
            queryStatement.setFromStatement(fromStatement);
            ConditionStatement conditionStatement;
            if (second == null) {
                conditionStatement = new ConditionStatement();
                conditionStatement.setLeft(new SymbolStatement.LetterStatement("rn"));
                conditionStatement.setOper(new OperStatement.LEQStatement());
                conditionStatement.setRight(first);
            } else {
                ConditionStatement leftConditionStatement = new ConditionStatement();
                leftConditionStatement.setLeft(new SymbolStatement.LetterStatement("rn"));
                leftConditionStatement.setOper(new OperStatement.GTStatement());
                leftConditionStatement.setRight(first);

                ConditionStatement rightConditionStatement = new ConditionStatement();
                rightConditionStatement.setLeft(new SymbolStatement.LetterStatement("rn"));
                rightConditionStatement.setOper(new OperStatement.LEQStatement());
                ConditionStatement plusConditionStatement = new ConditionStatement();
                plusConditionStatement.setLeft(first);
                plusConditionStatement.setOper(new OperStatement.ADDStatement());
                plusConditionStatement.setRight(second);
                rightConditionStatement.setRight(plusConditionStatement);

                conditionStatement = new ConditionStatement();
                conditionStatement.setLeft(leftConditionStatement);
                conditionStatement.setOper(new OperStatement.ANDStatement());
                conditionStatement.setRight(rightConditionStatement);
            }
            WhereStatement whereStatement = new WhereStatement();
            whereStatement.setStatement(conditionStatement);
            queryStatement.setWhereStatement(whereStatement);
            statement = queryStatement;
        }
        return super.toString(statement, assist, invokerList);
    }
}
