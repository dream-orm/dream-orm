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
 * clickhouse方言
 */
public class ToClickHouse extends ToPubSQL {
    private final Map<String, String> replaceMap = new HashMap<>();

    public ToClickHouse() {
        replaceMap.put("%Y", "%Y");
        replaceMap.put("%y", "%y");
        replaceMap.put("%c", "%m");
        replaceMap.put("%m", "%m");
        replaceMap.put("%d", "%d");
        replaceMap.put("%e", "%e");
        replaceMap.put("%H", "%H");
        replaceMap.put("%k", "%H");
        replaceMap.put("%h", "%I");
        replaceMap.put("%l", "%I");
        replaceMap.put("%i", "%M");
        replaceMap.put("%S", "%S");
        replaceMap.put("%s", "%S");
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
        Statement order = statement.getOrder();
        Statement separator = statement.getSeparator();
        Statement paramsStatement = statement.getParamsStatement();
        if (paramsStatement instanceof ListColumnStatement) {
            Statement[] columnList = ((ListColumnStatement) paramsStatement).getColumnList();
            if (columnList.length > 1) {
                FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
                concatStatement.setParamsStatement(statement.getParamsStatement());
                paramsStatement = concatStatement;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("arrayStringConcat(");
        Boolean asc = null;
        if (order != null && order instanceof OrderStatement) {
            asc = true;
            Statement orderParamStatement = ((OrderStatement) order).getStatement();
            if (orderParamStatement instanceof ListColumnStatement) {
                ListColumnStatement orderListColumnStatement = (ListColumnStatement) orderParamStatement;
                Statement orderColumnStatement = orderListColumnStatement.getColumnList()[0];
                if (orderColumnStatement instanceof OrderStatement.DescStatement) {
                    asc = false;
                }
            }
            if (asc) {
                builder.append("arraySort(");
            } else {
                builder.append("arrayReverseSort(");
            }
        }
        if (distinct) {
            builder.append("groupUniqArray(");
        } else {
            builder.append("groupArray(");
        }
        builder.append(toStr(paramsStatement, assist, invokerList));
        builder.append(")");
        if (asc != null) {
            builder.append(")");
        }
        builder.append(",");
        if (separator != null) {
            builder.append(toStr(separator, assist, invokerList));
        } else {
            builder.append("','");
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "positionUTF8(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2) {
            return "position(" + toStr(columnList[1], assist, invokerList) + "," + toStr(columnList[0], assist, invokerList) + ")";
        } else {
            return "position(" + toStr(columnList[1], assist, invokerList) + "," + toStr(columnList[0], assist, invokerList) + "," + toStr(columnList[2], assist, invokerList) + ")";
        }
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = AntlrUtil.replace(toStr(columnList[1], assist, invokerList), replaceMap);
        return "formatDateTime(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.ExtractStatement.WeekExtractStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toWeek(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "toDateTime(" + toStr(columnList[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(IntervalStatement.YearIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalYear(" + res + ")";
    }

    @Override
    protected String toString(IntervalStatement.QuarterIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalQuarter(" + res + ")";
    }

    @Override
    protected String toString(IntervalStatement.MonthIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalMonth(" + res + ")";
    }

    @Override
    protected String toString(IntervalStatement.WeekIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalWeek(" + res + ")";
    }

    @Override
    protected String toString(IntervalStatement.DayIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalDay(" + res + ")";
    }

    @Override
    protected String toString(IntervalStatement.HourIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalHour(" + res + ")";
    }

    @Override
    protected String toString(IntervalStatement.MinuteIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalMinute(" + res + ")";
    }

    @Override
    protected String toString(IntervalStatement.SecondIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "toIntervalSecond(" + res + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toInt64(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toString(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.FloatCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toFloat64(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.DoubleCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toFloat64(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.DecimalCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement paramStatement = statement.getParamStatement();
        Statement decimalStatement = null;
        if (paramStatement != null && paramStatement instanceof BraceStatement) {
            BraceStatement braceStatement = (BraceStatement) paramStatement;
            Statement braceParamStatement = braceStatement.getStatement();
            if (braceParamStatement != null && braceParamStatement instanceof ListColumnStatement) {
                ListColumnStatement listColumnStatement = (ListColumnStatement) braceParamStatement;
                Statement[] columnList = listColumnStatement.getColumnList();
                if (columnList.length == 2) {
                    decimalStatement = columnList[1];
                }
            }
        }
        return "toDecimal64(" + toStr(statement.getStatement(), assist, invokerList) + "," + (decimalStatement == null ? "0" : toStr(decimalStatement, assist, invokerList)) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDateTime(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.DateCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDate(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.TimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toTime(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toInt64(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toString(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.FloatConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toFloat64(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.DoubleConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toFloat64(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.DecimalConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement paramStatement = statement.getParamStatement();
        Statement decimalStatement = null;
        if (paramStatement != null && paramStatement instanceof BraceStatement) {
            BraceStatement braceStatement = (BraceStatement) paramStatement;
            Statement braceParamStatement = braceStatement.getStatement();
            if (braceParamStatement != null && braceParamStatement instanceof ListColumnStatement) {
                ListColumnStatement listColumnStatement = (ListColumnStatement) braceParamStatement;
                Statement[] columnList = listColumnStatement.getColumnList();
                if (columnList.length == 2) {
                    decimalStatement = columnList[1];
                }
            }
        }
        return "toDecimal64(" + toStr(statement.getStatement(), assist, invokerList) + "," + (decimalStatement == null ? "0" : toStr(decimalStatement, assist, invokerList)) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateTimeConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDateTime(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDate(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.TimeConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toTime(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatWsStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String link = toStr(columnList[0], assist, invokerList);
        int i;
        ListColumnStatement listColumnStatement = new ListColumnStatement("," + link + ",");
        for (i = 1; i <= columnList.length - 1; i++) {
            listColumnStatement.add(columnList[i]);
        }
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        concatStatement.setParamsStatement(listColumnStatement);
        return toStr(concatStatement, assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.LtrimStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "trimLeft(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RtrimStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "trimRight(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TrimStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "trimBoth(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LPAD(' '," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, Assist assist, List<Invoker> invokerList) {
        return "rand()/4294967295.0";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(10," + s2 + "))*POWER(10,-" + s2 + ")";
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "today()";
    }

    @Override
    protected String toString(FunctionStatement.CurrentDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "today()";
    }

    @Override
    protected String toString(FunctionStatement.CurTimeStatement statement, Assist assist, List<Invoker> invokerList) {
        return "now()";
    }

    @Override
    protected String toString(FunctionStatement.CurrentTimeStatement statement, Assist assist, List<Invoker> invokerList) {
        return "now()";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDayOfMonth(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDayOfWeek(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDayOfYear(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toHour(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toMinute(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(toStartOfMonth(plus(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",toIntervalMonth(1))),toIntervalDay(1))";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toMonth(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SysDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "now()";
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toQuarter(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toSecond(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toWeek(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toYear(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(OperStatement.LLMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return "bitShiftLeft(" + left + "," + right + ")";
    }

    @Override
    protected String toString(OperStatement.RRMStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return "bitShiftRight(" + left + "," + right + ")";
    }

    @Override
    protected String toString(OperStatement.BITANDStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return "bitAnd(" + toStr(conditionStatement.getLeft(), assist, invokerList) + "," + toStr(conditionStatement.getRight(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(OperStatement.BITORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return "bitOr(" + toStr(conditionStatement.getLeft(), assist, invokerList) + "," + toStr(conditionStatement.getRight(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return "bitXor(" + toStr(conditionStatement.getLeft(), assist, invokerList) + "," + toStr(conditionStatement.getRight(), assist, invokerList) + ")";
    }
}
