package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;

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
            statement.setLimitStatement(null);
            if (second != null) {
                //查询字段
                ListColumnStatement listColumnStatement = AntlrUtil.listColumnStatement(",", AntlrUtil.aliasStatement("ROWNUM", "V_R_N"), AntlrUtil.listColumnStatement(".", "V_T", "*"));
                //查询表
                AliasStatement tableAliasStatement = AntlrUtil.aliasStatement(new BraceStatement(statement), new SymbolStatement.LetterStatement("V_T"));
                //查询条件
                ConditionStatement conditionStatement = AntlrUtil.conditionStatement(new SymbolStatement.LetterStatement("ROWNUM"), new OperStatement.LEQStatement(), AntlrUtil.conditionStatement(first, new OperStatement.ADDStatement(), second));
                //构建查询
                QueryStatement queryStatement = AntlrUtil.queryStatement(listColumnStatement, tableAliasStatement, conditionStatement);
                // 再包装一层
                //查询字段
                ListColumnStatement listColumnStatement1 = AntlrUtil.listColumnStatement(",", "*");
                //查询表
                AliasStatement tableAliasStatement1 = AntlrUtil.aliasStatement(new BraceStatement(queryStatement), null);
                //查询条件
                ConditionStatement conditionStatement1 = AntlrUtil.conditionStatement(new SymbolStatement.LetterStatement("V_R_N"), new OperStatement.GTStatement(), first);
                //构建查询
                statement = AntlrUtil.queryStatement(listColumnStatement1, tableAliasStatement1, conditionStatement1);
            } else {
                //查询字段
                ListColumnStatement listColumnStatement = AntlrUtil.listColumnStatement(",", "*");
                //查询表
                BraceStatement braceStatement = new BraceStatement(statement);
                //查询条件
                ConditionStatement conditionStatement = AntlrUtil.conditionStatement(new SymbolStatement.LetterStatement("ROWNUM"), new OperStatement.LEQStatement(), first);
                //构建查询
                statement = AntlrUtil.queryStatement(listColumnStatement, braceStatement, conditionStatement);
            }
        }
        return super.toString(statement, assist, invokerList);
    }
}
