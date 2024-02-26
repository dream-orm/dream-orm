package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;

import java.util.List;

/**
 * oracle11方言
 */
public class ToOracle11 extends ToOracle {
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
            if (second == null) {
                second = first;
                first = new SymbolStatement.LetterStatement("0");
            }
            statement.setLimitStatement(null);

            QueryStatement queryStatement = new QueryStatement();
            SelectStatement selectStatement = new SelectStatement();
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            listColumnStatement.add(new SymbolStatement.LetterStatement("ROWNUM V_R_N"));
            listColumnStatement.add(new SymbolStatement.LetterStatement("V_T0.*"));
            selectStatement.setSelectList(listColumnStatement);
            queryStatement.setSelectStatement(selectStatement);
            AliasStatement tableAliasStatement = new AliasStatement();
            tableAliasStatement.setColumn(new BraceStatement(statement));
            tableAliasStatement.setAlias(new SymbolStatement.LetterStatement("V_T0"));
            FromStatement fromStatement = new FromStatement();
            fromStatement.setMainTable(tableAliasStatement);
            queryStatement.setFromStatement(fromStatement);
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.LetterStatement("ROWNUM"));
            conditionStatement.setOper(new OperStatement.LEQStatement());
            ConditionStatement plusConditionStatement = new ConditionStatement();
            plusConditionStatement.setLeft(first);
            plusConditionStatement.setOper(new OperStatement.ADDStatement());
            plusConditionStatement.setRight(second);
            conditionStatement.setRight(plusConditionStatement);
            WhereStatement whereStatement = new WhereStatement();
            whereStatement.setStatement(conditionStatement);
            queryStatement.setWhereStatement(whereStatement);


            // 再包装一层
            QueryStatement queryStatement1 = new QueryStatement();
            SelectStatement selectStatement1 = new SelectStatement();
            ListColumnStatement listColumnStatement1 = new ListColumnStatement(",");
            listColumnStatement1.add(new SymbolStatement.LetterStatement("* "));
            selectStatement1.setSelectList(listColumnStatement1);
            queryStatement1.setSelectStatement(selectStatement1);
            AliasStatement tableAliasStatement1 = new AliasStatement();
            tableAliasStatement1.setColumn(new BraceStatement(queryStatement));
            tableAliasStatement1.setAlias(new SymbolStatement.LetterStatement("V_T1"));
            FromStatement fromStatement1 = new FromStatement();
            fromStatement1.setMainTable(tableAliasStatement1);
            queryStatement1.setFromStatement(fromStatement1);
            ConditionStatement conditionStatement1;
            conditionStatement1 = new ConditionStatement();
            conditionStatement1.setLeft(new SymbolStatement.LetterStatement("V_R_N"));
            conditionStatement1.setOper(new OperStatement.GTStatement());
            conditionStatement1.setRight(first);
            WhereStatement whereStatement1 = new WhereStatement();
            whereStatement1.setStatement(conditionStatement1);
            queryStatement1.setWhereStatement(whereStatement1);
            statement = queryStatement1;
        }
        return super.toString(statement, assist, invokerList);
    }
}
