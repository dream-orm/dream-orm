package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.expr.QueryExpr;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.util.AntlrUtil;

import java.util.List;

public class ToMSSQL extends ToPubSQL {

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "REPLICATE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CHARINDEX(" + toStr(columnList[1], assist, invokerList) + "," + toStr(columnList[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CHARINDEX(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = toStr(columnList[1], assist, invokerList);
            statement.setPattern(pattern);
        }
        return "FORMAT(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CONVERT(datetime," + toStr(columnList[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.YearDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(yy," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.YearDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(yy,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(qq," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(qq,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(mm," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(mm,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(wk," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(wk,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.DayDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(dd," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.DayDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(dd,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.HourDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(hh," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.HourDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(hh,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(mi," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(mi,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(ss," + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEADD(ss,-" + toStr(statement.getQty(), assist, invokerList) + "," + toStr(statement.getDate(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(CastTypeStatement.TimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIME)";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATETIME)";
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(INT," + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CONVERT(VARCHAR," + toStr(statement.getStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.SubStrStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String str = toStr(columnList[0], assist, invokerList);
        String start = toStr(columnList[1], assist, invokerList);
        String num2;
        if (columnList.length < 3) {
            num2 = "LEN(" + str + ")";
        } else {
            num2 = toStr(columnList[2], assist, invokerList);
        }

        return "SUBSTRING(" + str + "," + start + "," + num2 + ")";
    }

    @Override
    protected String toString(FunctionStatement.TrimStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RTRIM(LTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + "))";
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
                sql = "select t_tmp.* from(select row_number() over(order by(select 0)) rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn<=" + maxValue;
            } else {
                querySql = toNativeSQL.toStr(statement, null, null);
                maxValue = toNativeSQL.toStr(second, null, null);
                minValue = toNativeSQL.toStr(first, null, null);
                sql = "select t_tmp.* from(select row_number() over(order by(select 0)) rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn > " + minValue + " and rn<=" + minValue + "+" + maxValue;
            }
            QueryStatement queryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            AntlrUtil.copy(statement, queryStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LEN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LEN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LeftStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LEFT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RIGHT(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SPACE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String num = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList);
        String str2 = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[2], assist, invokerList);
        String str1 = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList);
        return "CASE WHEN LEN(" + str1 + ")<" + num + " THEN RIGHT(REPLICATE(" + str2 + "," + num + ")+" + str1 + "," + num + ") ELSE LEFT(" + str1 + "," + num + ") END";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String num = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList);
        return "LEFT(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "+REPLICATE("
                + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[2], assist, invokerList) + ","
                + num + ")," + num + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LnStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(2," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG10(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, Assist assist, List<Invoker> invokerList) {
        return "PI()";
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
        return "RAND()";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        if (((ListColumnStatement) statement.getParamsStatement()).getColumnList().length == 1) {
            return "ROUND(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",0)";
        } else {
            return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
        }
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(CONVERT(FLOAT,10)," + s2 + "))*POWER(CONVERT(FLOAT,10),-" + s2 + ")";
    }


    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }


    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "GETDATE()";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEDIFF(dd," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + "," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "dd," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "w," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "dy," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "hh," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "mi," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String s = toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList);
        return "DATEADD(dd,-DAY(" + s + "),DATEADD(m,1," + s + "))";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "m," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        throw new RuntimeException("尚未完成DATE");
    }

    @Override
    protected String toString(FunctionStatement.NowStatement statement, Assist assist, List<Invoker> invokerList) {
        return "GETDATE()";
    }

    @Override
    protected String toString(FunctionStatement.SysDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "GETDATE()";
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "q," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "s," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "ww," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "yy," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "ISNULL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
}
