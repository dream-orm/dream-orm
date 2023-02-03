package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.expr.QueryExpr;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.util.AntlrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToORACLE extends ToPubSQL {
    private Map<String, String> replaceMap = new HashMap<>();

    public ToORACLE() {
        replaceMap.put("%Y", "yyyy");
        replaceMap.put("%y", "yy");
        replaceMap.put("%c", "MM");
        replaceMap.put("%m", "MM");
        replaceMap.put("%d", "dd");
        replaceMap.put("%e", "dd");
        replaceMap.put("%h", "HH24");
        replaceMap.put("%k", "HH24");
        replaceMap.put("%h", "hh");
        replaceMap.put("%l", "hh");
        replaceMap.put("%i", "mi");
        replaceMap.put("%S", "ss");
        replaceMap.put("%s", "ss");
        replaceMap.put("%j", "ddd");
    }

    private String getPattern(String pattern) {
        return AntlrUtil.replace(pattern, replaceMap);
    }

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String tar = toStr(columnList[0], assist, invokerList);
        String num = toStr(columnList[1], assist, invokerList);
        return "LPAD(" + tar + ",LENGTH(" + tar + ")*" + num + "," + tar + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "INSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        Statement tempStatement = columnList[0];
        columnList[0] = columnList[1];
        columnList[1] = tempStatement;
        return "INSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "TO_CHAR(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(DateOperStatement.YearDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + "," + toStr(statement.getQty(), assist, invokerList) + "*12)";
    }

    @Override
    protected String toString(DateOperStatement.YearDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + ",-" + toStr(statement.getQty(), assist, invokerList) + "*12)";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + "," + toStr(statement.getQty(), assist, invokerList) + "*3)";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + ",-" + toStr(statement.getQty(), assist, invokerList) + "*3)";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + "," + toStr(statement.getQty(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + ",-" + toStr(statement.getQty(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+" + toStr(statement.getQty(), assist, invokerList) + "*7";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-" + toStr(statement.getQty(), assist, invokerList) + "*7";
    }

    @Override
    protected String toString(DateOperStatement.DayDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+" + toStr(statement.getQty(), assist, invokerList);
    }

    @Override
    protected String toString(DateOperStatement.DayDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-" + toStr(statement.getQty(), assist, invokerList);
    }

    @Override
    protected String toString(DateOperStatement.HourDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+" + toStr(statement.getQty(), assist, invokerList) + "/24";
    }

    @Override
    protected String toString(DateOperStatement.HourDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-" + toStr(statement.getQty(), assist, invokerList) + "/24";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+" + toStr(statement.getQty(), assist, invokerList) + "/1440";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-" + toStr(statement.getQty(), assist, invokerList) + "/1440";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+" + toStr(statement.getQty(), assist, invokerList) + "/86400";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-" + toStr(statement.getQty(), assist, invokerList) + "/86400";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(LimitStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        if (statement.isOffset()) {
            StringBuilder builder = new StringBuilder();
            if (statement.getSecond() != null) {
                builder.append(" OFFSET " + toStr(statement.getSecond(), assist, invokerList));
            }
            builder.append(" ROWS FETCH NEXT " + toStr(statement.getFirst(), assist, invokerList) + " ROWS ONLY");
            return builder.toString();
        } else {
            throw new AntlrException("不支持非offset分页");
        }
    }

    @Override
    protected String toString(QueryStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        LimitStatement limitStatement = statement.getLimitStatement();
        if (limitStatement != null && !limitStatement.isOffset()) {
            Statement first = limitStatement.getFirst();
            Statement second = limitStatement.getSecond();
            statement.setLimitStatement(null);
            ToSQL toNativeSQL = new ToNativeSQL();
            String minValue;
            String maxValue;
            String querySql = toNativeSQL.toStr(statement, null, null);
            String sql;
            if (second == null) {
                maxValue = toNativeSQL.toStr(first, null, null);
                sql = "select* from(select rownum rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn <=" + maxValue;
            } else {
                maxValue = toNativeSQL.toStr(second, null, null);
                minValue = toNativeSQL.toStr(first, null, null);
                sql = "select* from(select rownum rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn > " + minValue + " and rn<=" + minValue + "+" + maxValue;
            }
            QueryStatement queryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            AntlrUtil.copy(statement, queryStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2) {
            return super.toString(statement, assist, invokerList);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            for (i = 0; i < columnList.length - 1; i++) {
                stringBuilder.append(toStr(columnList[i], assist, invokerList) + "||");
            }
            stringBuilder.append(toStr(columnList[i], assist, invokerList));
            return stringBuilder.toString();
        }
    }

    @Override
    protected String toString(FunctionStatement.ConcatWsStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String link = toStr(columnList[0], assist, invokerList);
        int i;
        StringBuilder builder = new StringBuilder();
        for (i = 1; i < columnList.length - 1; i++) {
            builder.append(toStr(columnList[i], assist, invokerList) + "||" + link + "||");
        }
        builder.append(toStr(columnList[i], assist, invokerList));
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.LeftStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",1," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LPAD(' '," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        if (((ListColumnStatement) statement.getParamsStatement()).getColumnList().length == 1) {
            return "LN(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
        } else {
            return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
        }
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(2," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(10," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, Assist assist, List<Invoker> invokerList) {
        return "3.14159265358979";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowerStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, Assist assist, List<Invoker> invokerList) {
        return "DBMS_RANDOM.VALUE";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(10," + s2 + "))*POWER(10,-" + s2 + ")";
    }


    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'dd'))";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'d'))";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ddd'))";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24'))";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mi'))";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LAST_DAY(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mm'))";
    }

    @Override
    protected String toString(FunctionStatement.DateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        throw new RuntimeException("尚未完成DATE");
    }

    @Override
    protected String toString(FunctionStatement.NowStatement statement, Assist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.SysDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'q'))";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ss'))";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ww'" + "))";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'yyyy'" + "))";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "NVL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CASE WHEN " + toStr(columnList[0], assist, invokerList) + " THEN " + toStr(columnList[1], assist, invokerList) + " ELSE " + toStr(columnList[2], assist, invokerList) + " END";
    }

    @Override
    protected String toString(OperStatement.LLMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + "*POWER(2," + right + ")";
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + "/POWER(2," + right + ")";
    }

    @Override
    protected String toString(OperStatement.BITANDStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return "BITAND(" + toStr(conditionStatement.getLeft(), assist, invokerList) + "," + toStr(conditionStatement.getRight(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(OperStatement.BITORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + right + "-BITAND(" + left + "," + right + ")";
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + right + "-2*BITAND(" + left + "," + right + ")";
    }
}
