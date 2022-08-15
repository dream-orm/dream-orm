package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.QueryExpr;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.List;

public class ToPGSQL extends ToPubSQL {
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
    protected String toString(QueryStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
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
                sql = "select t_tmp.* from(select row_number() over(order by(select 0)) rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn<=" + maxValue;
            } else {
                querySql = toNativeSQL.toStr(statement, null, null);
                maxValue = toNativeSQL.toStr(second, null, null);
                minValue = toNativeSQL.toStr(first, null, null);
                sql = "select t_tmp.* from(select row_number() over(order by(select 0)) rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn>" + minValue + " and rn<=" + minValue + "+" + maxValue;
            }
            QueryStatement queryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            ReflectUtil.copy(statement, queryStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(SymbolStatement.SingleMarkStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "\"" + statement.getValue() + "\"";
    }

    @Override
    protected String toString(FunctionStatement.GroupConcatStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "STRING_AGG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",',')";
    }


    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String tar = toStr(columnList[0], assist, invokerList);
        String num = toStr(columnList[1], assist, invokerList);
        return "LPAD(" + tar + ",LENGTH(" + tar + ")*" + num + "," + tar + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "STRPOS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
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
    protected String toString(FunctionStatement.DateForMatStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }

        return "TO_CHAR(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")::TIMESTAMP";
    }

    @Override
    protected String toString(DateOperStatement.YearDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " year'";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
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
    protected String toString(DateOperStatement.MonthDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " month'";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " week'";
    }

    @Override
    protected String toString(DateOperStatement.DayDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " day'";
    }

    @Override
    protected String toString(DateOperStatement.HourDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " hour'";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " min'";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateOperStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + "'" + toStr(statement.getQty(), assist, invokerList) + " sec'";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIMESTAMP)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIMESTAMP)";
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LeftStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "LEFT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "RIGHT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }


    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "LPAD(' '," + toStr(statement.getParamsStatement(), assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "LPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "RTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        if (((ListColumnStatement) statement.getParamsStatement()).getColumnList().length == 1)
            return "LN(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
        else
            return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(2," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(10," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, Assist assist, List<Invoker> invokerList) {
        return "PI()";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "POW(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowerStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, Assist assist, List<Invoker> invokerList) {
        return "RANDOM()";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(10," + s2 + "))*POWER(10,-" + s2 + ")";
    }

    @Override
    protected String toString(FunctionStatement.UnixTimeStampStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "EXTRACT(epoch FROM " + toStr(statement.getParamsStatement(), assist, invokerList) + ")::INTEGER";
    }

    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "CURRENT_DATE";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "DATE_PART('day'," + toStr(columnList[0], assist, invokerList) + "-" + toStr(columnList[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'dd')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'d')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ddd') AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24'))AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mi')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "(DATE_TRUNC('month', " + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ") + INTERVAL '1 month - 1 day')::date";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mm')) AS INTEGER)";
    }


    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'q')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ss')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ww'" + ")) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'yyyy'" + ")) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        return "COALESCE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CASE WHEN " + toStr(columnList[0], assist, invokerList) + " THEN " + toStr(columnList[1], assist, invokerList) + " ELSE " + toStr(columnList[2], assist, invokerList) + " END";
    }

    @Override
    protected String toString(OperStatement.LLMStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<<" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">>" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, Assist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "#" + toStr(conditionStatement.getRight(), assist, invokerList);
    }
}
