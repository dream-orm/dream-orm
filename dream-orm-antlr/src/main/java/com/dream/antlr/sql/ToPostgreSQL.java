package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pgsql方言
 */
public class ToPostgreSQL extends ToPubSQL {
    private Map<String, String> replaceMap = new HashMap<>();

    public ToPostgreSQL() {
        replaceMap.put("%Y", "yyyy");
        replaceMap.put("%y", "yy");
        replaceMap.put("%c", "mm");
        replaceMap.put("%m", "mm");
        replaceMap.put("%d", "dd");
        replaceMap.put("%e", "dd");
        replaceMap.put("%H", "hh24");
        replaceMap.put("%k", "hh24");
        replaceMap.put("%h", "hh");
        replaceMap.put("%l", "hh");
        replaceMap.put("%i", "mi");
        replaceMap.put("%S", "ss");
        replaceMap.put("%s", "ss");
        replaceMap.put("%j", "ddd");
        replaceMap.put("%T", "hh24:mi:ss");
        replaceMap.put("%W", "Day");
        replaceMap.put("%a", "Dy");
        replaceMap.put("%M", "Month");
        replaceMap.put("%b", "Mon");
        replaceMap.put("%v", "iw");
    }

    @Override
    protected String toString(InsertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement columns = statement.getColumns();
        return "INSERT INTO " + toStr(statement.getTable(), assist, invokerList) + (columns != null ? toStr(columns, assist, invokerList) : " ") + toStr(statement.getValues(), assist, invokerList);
    }

    @Override
    protected String toString(ReplaceIntoStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement columns = statement.getColumns();
        return "INSERT INTO " + toStr(statement.getTable(), assist, invokerList) + (columns != null ? toStr(columns, assist, invokerList) : " ") + toStr(statement.getValues(), assist, invokerList);
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
    protected String toString(FunctionStatement.LcaseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.UcaseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "UPPER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
        String pattern = AntlrUtil.replace(toStr(columnList[1], assist, invokerList), replaceMap);
        return "TO_CHAR(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = AntlrUtil.replace(toStr(columnList[1], assist, invokerList), replaceMap);
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")::TIMESTAMP";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.DoubleCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS NUMERIC)";
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
    protected String toString(ConvertTypeStatement.FloatConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS FLOAT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DoubleConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS NUMERIC)";
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
    protected String toString(FunctionStatement.CharLengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "RTRIM(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        if (((ListColumnStatement) statement.getParamsStatement()).getColumnList().length == 1) {
            return "LN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
        } else {
            return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
        }
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(2," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(10," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, Assist assist, List<Invoker> invokerList) {
        return "RANDOM()";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(10," + s2 + "))*POWER(10,-" + s2 + ")";
    }

    @Override
    protected String toString(IntervalStatement.YearIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + res + " YEAR'";
    }

    @Override
    protected String toString(IntervalStatement.QuarterIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + Integer.parseInt(res) * 3 + " MONTH'";

    }

    @Override
    protected String toString(IntervalStatement.MonthIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + res + " MONTH'";
    }

    @Override
    protected String toString(IntervalStatement.WeekIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + res + " WEEK'";
    }

    @Override
    protected String toString(IntervalStatement.DayIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + res + " DAY'";
    }

    @Override
    protected String toString(IntervalStatement.HourIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + res + " HOUR'";
    }

    @Override
    protected String toString(IntervalStatement.MinuteIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + res + " MINUTE'";
    }

    @Override
    protected String toString(IntervalStatement.SecondIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + res + " SECOND'";
    }

    @Override
    protected String toString(LimitStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        StringBuilder builder = new StringBuilder();
        if (statement.isOffset()) {
            builder.append("LIMIT " + toStr(statement.getFirst(), assist, invokerList));
            if (statement.getSecond() != null) {
                builder.append(" OFFSET " + toStr(statement.getSecond(), assist, invokerList));
            }
        } else {
            if (statement.getSecond() != null) {
                builder.append("LIMIT " + toStr(statement.getSecond(), assist, invokerList));
                builder.append(" OFFSET " + toStr(statement.getFirst(), assist, invokerList));
            } else {
                builder.append("LIMIT " + toStr(statement.getFirst(), assist, invokerList));
            }
        }
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.UnixTimeStampStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "EXTRACT(epoch FROM " + toStr(statement.getParamsStatement(), assist, invokerList) + ")::INTEGER";
    }

    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement paramsStatement = (ListColumnStatement) statement.getParamsStatement();
        Statement[] columnList = paramsStatement.getColumnList();
        return toStr(columnList[0], assist, invokerList) + "+" + toStr(columnList[1], assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.DateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement paramsStatement = (ListColumnStatement) statement.getParamsStatement();
        Statement[] columnList = paramsStatement.getColumnList();
        return toStr(columnList[0], assist, invokerList) + "-" + toStr(columnList[1], assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "CURRENT_DATE";
    }

    @Override
    protected String toString(FunctionStatement.CurrentDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "CURRENT_DATE";
    }

    @Override
    protected String toString(FunctionStatement.CurTimeStatement statement, Assist assist, List<Invoker> invokerList) {
        return "CURRENT_TIME";
    }

    @Override
    protected String toString(FunctionStatement.CurrentTimeStatement statement, Assist assist, List<Invoker> invokerList) {
        return "CURRENT_TIME";
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
        return "CAST(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'ddd') AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24'))AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'mi')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "(DATE_TRUNC('month', " + toStr(statement.getParamsStatement(), assist, invokerList) + ") + INTERVAL '1 month - 1 day')::date";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'mm')) AS INTEGER)";
    }


    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'q')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'ss')) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'ww'" + ")) AS INTEGER)";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST((TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'yyyy'" + ")) AS INTEGER)";
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
    protected String toString(OperStatement.BITXORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return toStr(conditionStatement.getLeft(), assist, invokerList) + "#" + toStr(conditionStatement.getRight(), assist, invokerList);
    }
}
