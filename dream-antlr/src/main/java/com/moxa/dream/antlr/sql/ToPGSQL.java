package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;

import java.util.List;

public class ToPGSQL extends ToPubSQL {
    private final int num = 1;

    private String getPattern(String pattern) {
        char[] patternArray = pattern.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < patternArray.length; i++) {
            if (patternArray[i] == '%') {
                char sign = patternArray[i + 1];
                switch (sign) {
                    case 'Y':
                        builder.append("yyyy");
                        break;
                    case 'y':
                        builder.append("yy");
                        break;
                    case 'c':
                    case 'm':
                        builder.append("MM");
                        break;
                    case 'd':
                    case 'e':
                        builder.append("dd");
                        break;
                    case 'H':
                    case 'k':
                        builder.append("HH24");
                        break;
                    case 'h':
                    case 'l':
                        builder.append("hh");
                        break;
                    case 'i':
                        builder.append("mi");
                        break;
                    case 'S':
                    case 's':
                        builder.append("ss");
                        break;
                    case 'j':
                        builder.append("ddd");
                        break;
                    default:
                        builder.append(patternArray[i + 1]);
                        break;

                }
                i++;
            } else builder.append(patternArray[i]);
        }
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.ToCharStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2) {
            return "TO_CHAR(" + toStr(columnList[0], assist, invokerList) + "," + toStr(columnList[1], assist, invokerList) + ")";
        } else
            return "CAST(" + toStr(columnList[0], assist, invokerList) + " AS VARCHAR)";
    }

    protected String toString(FunctionStatement.ToNumberStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getParamsStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String tar = toStr(columnList[0], assist, invokerList);
        String num = toStr(columnList[1], assist, invokerList);
        return "LPAD(" + tar + ",LENGTH(" + tar + ")*" + num + "," + tar + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "STRPOS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2)
            return "STRPOS(" + toStr(columnList[1], assist, invokerList) + "," + toStr(columnList[0], assist, invokerList) + ")";
        else {
            String targetStr = toStr(columnList[1], assist, invokerList);
            String likeStr = toStr(columnList[0], assist, invokerList);
            String point = toStr(columnList[2], assist, invokerList);
            String search = "STRPOS(SUBSTRING(" + targetStr + "," + point + ")," + likeStr + ")";
            return "CASE " + search + "+" + point + " WHEN " + point + " THEN 0 ELSE " + search + "+" + point + "-1 END";
        }
    }

    @Override
    protected String toString(FunctionStatement.ToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + toStr(columnList[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }

        return "TO_CHAR(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(DateOperStatement.YearDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " year'";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        String num = toStr(statement.getQty(), assist, invokerList);
        return toStr(statement.getDate(), assist, invokerList)
                + type + "'" + num + " month'"
                + type + "'" + num + " month'"
                + type + "'" + num + " month'";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " month'";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " week'";
    }

    @Override
    protected String toString(DateOperStatement.DayDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " day'";
    }

    @Override
    protected String toString(DateOperStatement.HourDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " hour'";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " min'";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " sec'";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIMESTAMP)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIMESTAMP)";
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LeftStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LEFT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "RIGHT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }


    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LPAD(' '," + toStr(statement.getParamsStatement(), assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "RTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        if (((ListColumnStatement) statement.getParamsStatement()).getColumnList().length == 1)
            return "LN(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
        else
            return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(2," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(10," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "PI()";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "POW(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "RANDOM()";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(10," + s2 + "))*POWER(10,-" + s2 + ")";
    }


    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "CURRENT_DATE";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "DATE_PART('day'," + toStr(columnList[0], assist, invokerList) + "-" + toStr(columnList[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'dd')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'d')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ddd') AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24'))AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mi')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "(DATE_TRUNC('month', " + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ") + INTERVAL '1 month - 1 day')::date";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mm')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.DateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new RuntimeException("尚未完成DATE");
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'q')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ss')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ww'" + ")) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'yyyy'" + ")) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "COALESCE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CASE WHEN " + toStr(columnList[0], assist, invokerList) + " THEN " + toStr(columnList[1], assist, invokerList) + " ELSE " + toStr(columnList[2], assist, invokerList) + " END";
    }

    @Override
    protected String toString(OperStatement.LLMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<<" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">>" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "#" + toStr(conditionStatement.getRight(), assist, invokerList);
    }
}
