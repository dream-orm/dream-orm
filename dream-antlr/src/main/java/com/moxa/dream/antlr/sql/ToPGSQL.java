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

public class ToPGSQL extends ToPubSQL {
    private Map<String, String> replaceMap = new HashMap<>();

    public ToPGSQL() {
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
                sql = "select t_tmp.* from(select row_number() over(order by(select 0)) rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn>" + minValue + " and rn<=" + minValue + "+" + maxValue;
            }
            QueryStatement queryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            AntlrUtil.copy(statement, queryStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(SymbolStatement.SingleMarkStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "\"" + statement.getValue() + "\"";
    }

    @Override
    protected String toString(FunctionStatement.GroupConcatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        boolean distinct = statement.isDistinct();
        boolean all = statement.isAll();
        Statement order = statement.getOrder();
        Statement separator = statement.getSeparator();
        StringBuilder builder = new StringBuilder();
        if (distinct) {
            builder.append("DISTINCT ");
        }
        if (all) {
            builder.append("ALL ");
        }
        builder.append(toStr(statement.getParamsStatement(), assist, invokerList));
        if (separator != null) {
            builder.append("," + toStr(separator, assist, invokerList));
        } else {
            builder.append(",','");
        }
        if (order != null) {
            builder.append(" " + toStr(order, assist, invokerList));
        }
        return "STRING_AGG(" + builder + ")";
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
        return "STRPOS(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2) {
            return "STRPOS(" + toStr(columnList[1], assist, invokerList) + "," + toStr(columnList[0], assist, invokerList) + ")";
        } else {
            String targetStr = toStr(columnList[1], assist, invokerList);
            String likeStr = toStr(columnList[0], assist, invokerList);
            String point = toStr(columnList[2], assist, invokerList);
            String search = "STRPOS(SUBSTRING(" + targetStr + "," + point + ")," + likeStr + ")";
            return "CASE " + search + "+" + point + " WHEN " + point + " THEN 0 ELSE " + search + "+" + point + "-1 END";
        }
    }

    @Override
    protected String toString(OperStatement.DIVIDEStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return "CAST(" + toStr(conditionStatement.getLeft(), assist, invokerList) + " as DECIMAL)/" + toStr(conditionStatement.getRight(), assist, invokerList);
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
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")::TIMESTAMP";
    }

    @Override
    protected String toString(DateOperStatement.YearDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+'" + toStr(statement.getQty(), assist, invokerList) + " year'";
    }

    @Override
    protected String toString(DateOperStatement.YearDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-'" + toStr(statement.getQty(), assist, invokerList) + " year'";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String num = toStr(statement.getQty(), assist, invokerList);
        return toStr(statement.getDate(), assist, invokerList)
                + "+'" + num + " month'"
                + "+'" + num + " month'"
                + "+'" + num + " month'";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String num = toStr(statement.getQty(), assist, invokerList);
        return toStr(statement.getDate(), assist, invokerList)
                + "-'" + num + " month'"
                + "-'" + num + " month'"
                + "-'" + num + " month'";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+'" + toStr(statement.getQty(), assist, invokerList) + " month'";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-'" + toStr(statement.getQty(), assist, invokerList) + " month'";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+'" + toStr(statement.getQty(), assist, invokerList) + " week'";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-'" + toStr(statement.getQty(), assist, invokerList) + " week'";
    }

    @Override
    protected String toString(DateOperStatement.DayDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+'" + toStr(statement.getQty(), assist, invokerList) + " day'";
    }

    @Override
    protected String toString(DateOperStatement.DayDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-'" + toStr(statement.getQty(), assist, invokerList) + " day'";
    }

    @Override
    protected String toString(DateOperStatement.HourDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+'" + toStr(statement.getQty(), assist, invokerList) + " hour'";
    }

    @Override
    protected String toString(DateOperStatement.HourDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-'" + toStr(statement.getQty(), assist, invokerList) + " hour'";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+'" + toStr(statement.getQty(), assist, invokerList) + " min'";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-'" + toStr(statement.getQty(), assist, invokerList) + " min'";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "+'" + toStr(statement.getQty(), assist, invokerList) + " sec'";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getDate(), assist, invokerList) + "-'" + toStr(statement.getQty(), assist, invokerList) + " sec'";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIMESTAMP)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIMESTAMP)";
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS VARCHAR)";
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
        return "LPAD(' '," + toStr(statement.getParamsStatement(), assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEILING(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
        return "PI()";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POW(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowerStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, Assist assist, List<Invoker> invokerList) {
        return "RANDOM()";
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
    protected String toString(FunctionStatement.UnixTimeStampStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "EXTRACT(epoch FROM " + toStr(statement.getParamsStatement(), assist, invokerList) + ")::INTEGER";
    }

    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "CURRENT_DATE";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "DATE_PART('day'," + toStr(columnList[0], assist, invokerList) + "-" + toStr(columnList[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'dd')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'d')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ddd') AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24'))AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mi')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "(DATE_TRUNC('month', " + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ") + INTERVAL '1 month - 1 day')::date";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mm')) AS INTEGER)";
    }


    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'q')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ss')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ww'" + ")) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'yyyy'" + ")) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "COALESCE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "CASE WHEN " + toStr(columnList[0], assist, invokerList) + " THEN " + toStr(columnList[1], assist, invokerList) + " ELSE " + toStr(columnList[2], assist, invokerList) + " END";
    }

    @Override
    protected String toString(FunctionStatement.ToCharStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 1) {
            return "CAST(" + toStr(columnList[0], assist, invokerList) + " AS VARCHAR)";
        } else {
            return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
        }
    }

    @Override
    protected String toString(FunctionStatement.ToNumberStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 1) {
            return "CAST(" + toStr(columnList[0], assist, invokerList) + " AS DECIMAL)";
        } else {
            return "TO_NUMBER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
        }
    }


    @Override
    protected String toString(OperStatement.LLMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "<<" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + ">>" + toStr(conditionStatement.getRight(), assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "#" + toStr(conditionStatement.getRight(), assist, invokerList);
    }
}
