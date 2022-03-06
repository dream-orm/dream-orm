package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.QueryExpr;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.util.ExprUtil;

import java.util.List;

public class ToMSSQL extends ToPubSQL {
    private int num = 1;

    private String getPattern(String pattern) {
        char[] patternArray = pattern.toCharArray();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < patternArray.length) {
            char sign = Character.toUpperCase(patternArray[i]);
            switch (sign) {
                case 'Y':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'Y':
                            switch (Character.toUpperCase(patternArray[i + 2])) {
                                case 'Y':
                                    switch (Character.toUpperCase(patternArray[i + 3])) {
                                        case 'Y':
                                            builder.append("yyyy");
                                            i += 4;
                                            break;
                                        default:
                                            builder.append("yy");
                                            i += 2;
                                            break;
                                    }
                                    break;
                                default:
                                    builder.append("yy");
                                    i += 2;
                                    break;
                            }
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'M':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'M':
                            builder.append("mm");
                            i += 2;
                            break;
                        case 'I':
                            builder.append("mi");
                            i += 2;
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'D':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'D':
                            builder.append("dd");
                            i += 2;
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'H':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'H':
                            switch (Character.toUpperCase(patternArray[i + 2])) {
                                case '2':
                                    switch (Character.toUpperCase(patternArray[i + 3])) {
                                        case '4':
                                            builder.append("HH");
                                            i += 4;
                                            break;
                                        default:
                                            builder.append("hh");
                                            i += 2;
                                            break;
                                    }
                                    break;
                                case '1':
                                    switch (Character.toUpperCase(patternArray[i + 3])) {
                                        case '2':
                                            builder.append("hh");
                                            i += 4;
                                            break;
                                        default:
                                            builder.append("hh");
                                            i += 2;
                                            break;
                                    }
                                    break;
                                default:
                                    builder.append("hh");
                                    i += 2;
                                    break;
                            }
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                case 'S':
                    switch (Character.toUpperCase(patternArray[i + 1])) {
                        case 'S':
                            builder.append("ss");
                            i += 2;
                            break;
                        default:
                            builder.append(patternArray[i++]);
                            break;
                    }
                    break;
                default:
                    builder.append(patternArray[i++]);
                    break;
            }
        }
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.ToCharStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2) {
            String pattern = statement.getPattern();
            if (pattern == null) {
                pattern = getPattern(toStr(columnList[1], assist, invokerList));
                statement.setPattern(pattern);
            }
            return "FORMAT(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
        } else
            return "CONVERT(VARCHAR," + toStr(columnList[0], assist, invokerList) + ")";
    }

    protected String toString(FunctionStatement.ToNumberStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(INT," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "REPLICATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CHARINDEX(" + toStr(columnList[1], assist, invokerList) + "," + toStr(columnList[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CHARINDEX(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CONVERT(datetime," + toStr(columnList[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = toStr(columnList[1], assist, invokerList);
            statement.setPattern(pattern);
        }
        return "FORMAT(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CONVERT(datetime," + toStr(columnList[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.YearDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(yy," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(qq," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(mm," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(wk," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.DayDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(dd," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.HourDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(hh," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(mi," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "DATEADD(ss," + type + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(CastTypeStatement.TimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIME)";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATETIME)";
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(INT," + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CONVERT(VARCHAR," + toStr(statement.getStatement(), assist, invokerList) + ")";
    }


    @Override
    protected String toString(LimitStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        StringBuilder builder = new StringBuilder();
        builder.append(" OFFSET " + toStr(statement.getFirst(), assist, invokerList));
        if (statement.getSecond() != null) {
            builder.append(" ROWS FETCH NEXT " + toStr(statement.getSecond(), assist, invokerList) + " ROWS ONLY");
        }
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.SubStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String str = toStr(columnList[0], assist, invokerList);
        String start = toStr(columnList[1], assist, invokerList);
        String num2;
        if (columnList.length < 3) {
            num2 = "LEN(" + str + ")";
        } else
            num2 = toStr(columnList[2], assist, invokerList);

        return "SUBSTRING(" + str + "," + start + "," + num2 + ")";
    }

    @Override
    protected String toString(FunctionStatement.TrimStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "RTRIM(LTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(QueryStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        LimitStatement limitStatement = statement.getLimitStatement();
        if (limitStatement != null && !limitStatement.isOffset()) {
            Statement first = limitStatement.getFirst();
            Statement second = limitStatement.getSecond();
            statement.setLimitStatement(null);
            ToSQL toDREAM = new ToDREAM();
            String minValue;
            String maxValue;
            String querySql = toDREAM.toStr(statement, null, null);
            String sql;
            if (second == null) {
                maxValue = toDREAM.toStr(first, null, null);
                sql = "select t_tmp.* from(select row_number() over(order by(select 0)) rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn<=" + maxValue;
            } else {
                querySql = toDREAM.toStr(statement, null, null);
                maxValue = toDREAM.toStr(second, null, null);
                minValue = toDREAM.toStr(first, null, null);
                sql = "select t_tmp.* from(select row_number() over(order by(select 0)) rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn between " + minValue + " and " + minValue + "+" + maxValue;
            }
            QueryStatement queryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            ExprUtil.copy(statement, queryStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LEN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
        return "SPACE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String num = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList);
        String str2 = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[2], assist, invokerList);
        String str1 = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList);
        return "CASE WHEN LEN(" + str1 + ")<" + num + " THEN RIGHT(REPLICATE(" + str2 + "," + num + ")+" + str1 + "," + num + ") ELSE LEFT(" + str1 + "," + num + ") END";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String num = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList);
        return "LEFT(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "+REPLICATE("
                + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[2], assist, invokerList) + ","
                + num + ")," + num + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LnStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(2," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG10(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "PI()";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "RAND()";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        if (((ListColumnStatement) statement.getParamsStatement()).getColumnList().length == 1)
            return "ROUND(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",0)";
        else return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(CONVERT(FLOAT,10)," + s2 + "))*POWER(CONVERT(FLOAT,10),-" + s2 + ")";
    }


    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }


    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "GETDATE()";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEDIFF(dd," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + "," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "dd," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "w," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "dy," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "hh," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "mi," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String s = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList);
        return "DATEADD(dd,-DAY(" + s + "),DATEADD(m,1," + s + "))";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "m," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new RuntimeException("尚未完成DATE");
    }

    @Override
    protected String toString(FunctionStatement.NowStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "GETDATE()";
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "q," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "s," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "ww," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "DATEPART(" + "yy," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ISNULL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CASE WHEN " + toStr(columnList[0], assist, invokerList) + " THEN " + toStr(columnList[1], assist, invokerList) + " ELSE " + toStr(columnList[2], assist, invokerList) + " END";
    }

    @Override
    protected String toString(OperStatement.LLMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + "*POWER(2," + right + ")";
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + "/POWER(2," + right + ")";
    }
}
