package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.config.ExprType;
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
    private Map<String, String> replaceMap = new HashMap<>();

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

    private String getPattern(String pattern) {
        return AntlrUtil.replace(pattern, replaceMap);
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
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "formatDateTime(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "toDateTime(" + toStr(columnList[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.YearDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalYear(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.YearDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalYear(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalQuarter(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalQuarter(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalMonth(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalMonth(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalWeek(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalWeek(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.DayDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalDay(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.DayDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalDay(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.HourDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalHour(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.HourDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalHour(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalMinute(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalMinute(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "plus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalSecond(" + toStr(statement.getQty(), assist, invokerList) + "))";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "minus(" + toStr(statement.getDate(), assist, invokerList) + ",toIntervalSecond(" + toStr(statement.getQty(), assist, invokerList) + "))";
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
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDateTime(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.DateCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDate(" + toStr(statement.getStatement(), assist, invokerList) + ")";
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
    protected String toString(ConvertTypeStatement.DateTimeConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDateTime(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "toDate(" + toStr(statement.getStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.DateAddStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "today()";
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

    @Override
    protected String toString(DDLCreateStatement.DDLCreateTableStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStringForCreateTable(statement, assist, invokerList);
    }

    @Override
    protected String toString(DDLDefineStatement.DDLColumnDefineStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement column = statement.getColumn();
        ExprType columnType = statement.getColumnType();
        ListColumnStatement columnTypeParamList = statement.getColumnTypeParamList();
        Statement defaultValue = statement.getDefaultValue();
        String columnTypeName = columnType.name();
        boolean autoIncrement = statement.isAutoIncrement();
        boolean nullFlag = statement.isNullFlag();
        boolean primaryKey = statement.isPrimaryKey();
        StringBuilder builder = new StringBuilder();
        if (columnTypeParamList != null) {
            builder.append("(" + toStr(columnTypeParamList, assist, invokerList) + ")");
        }
        if (autoIncrement) {
            builder.append(" GENERATED BY DEFAULT on null AS IDENTITY");
        }
        if (!nullFlag) {
            builder.append(" NOT NULL");
        }
        if (primaryKey) {
            builder.append(" PRIMARY KEY");
        }
        if (defaultValue != null) {
            builder.append(" DEFAULT " + toStr(defaultValue, assist, invokerList));
        }
        return toStr(column, assist, invokerList) + " " + columnTypeName + builder;
    }
}
