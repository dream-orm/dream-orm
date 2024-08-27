package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * oracle方言
 */
public class ToOracle extends ToPubSQL {
    private final Map<String, String> replaceMap = new HashMap<>();

    public ToOracle() {
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
        Statement tableStatement = statement.getTable();
        Statement columnsStatement = statement.getColumns();
        Statement valuesStatement = statement.getValues();
        String table = toStr(tableStatement, assist, invokerList);
        String column = toStr(columnsStatement, assist, invokerList);
        String value = toStr(valuesStatement, assist, invokerList);
        if ("".equals(column)) {
            column = " ";
        }
        if (valuesStatement instanceof InsertStatement.ValuesStatement) {
            String paramValue = value.substring(7, value.length() - 1);
            String[] paramValueSplit = paramValue.split("\\),\\(");
            if (paramValueSplit.length > 1) {
                StringBuilder builder = new StringBuilder();
                for (String item : paramValueSplit) {
                    builder.append(" INTO ").append(table).append(column).append("VALUES").append("(").append(item).append(")");
                }
                return "INSERT ALL" + builder + " SELECT 1 FROM DUAL";
            }
        }
        return "INSERT INTO " + table + column + value;
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
        }
        String orderStr;
        if (order != null) {
            orderStr = toStr(order, assist, invokerList);
        } else {
            orderStr = "ORDER BY 0";
        }
        return "LISTAGG(" + builder + ") WITHIN GROUP(" + orderStr + ")";
    }

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String tar = toStr(columnList[0], assist, invokerList);
        String num = toStr(columnList[1], assist, invokerList);
        return "LPAD(" + tar + ",LENGTH(" + tar + ")*" + num + "," + tar + ")";
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
        String pattern = AntlrUtil.replace(toStr(columnList[1], assist, invokerList), replaceMap);
        return "TO_CHAR(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.ExtractStatement.QuarterExtractStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ",'q')";
    }

    @Override
    protected String toString(FunctionStatement.ExtractStatement.WeekExtractStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ",'ww')";
    }

    @Override
    protected String toString(FunctionStatement.ExtractStatement.HourExtractStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ",'hh24'" + ")";
    }

    @Override
    protected String toString(FunctionStatement.ExtractStatement.MinuteExtractStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ",'mi'" + ")";
    }

    @Override
    protected String toString(FunctionStatement.ExtractStatement.SecondExtractStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ",'ss'" + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = AntlrUtil.replace(toStr(columnList[1], assist, invokerList), replaceMap);
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
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
    protected String toString(CastTypeStatement.DateCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_DATE(" + toStr(statement.getStatement(), assist, invokerList) + ",'yyyy-mm-dd')";
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_DATE(" + toStr(statement.getStatement(), assist, invokerList) + ",'yyyy-mm-dd hh24:mi:ss')";
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
    protected String toString(ConvertTypeStatement.FloatConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS FLOAT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.DoubleConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.DoubleCastStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.DecimalConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DECIMAL" + toStr(statement.getParamStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_DATE(" + toStr(statement.getStatement(), assist, invokerList) + ",'yyyy-mm-dd')";
    }

    @Override
    protected String toString(ConvertTypeStatement.DateTimeConvertStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_DATE(" + toStr(statement.getStatement(), assist, invokerList) + ",'yyyy-mm-dd hh24:mi:ss')";
    }

    @Override
    protected String toString(LimitStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        StringBuilder builder = new StringBuilder();
        if (statement.isOffset()) {
            if (statement.getSecond() != null) {
                builder.append(" OFFSET " + toStr(statement.getSecond(), assist, invokerList));
            } else {
                builder.append(" OFFSET 0");
            }
            builder.append(" ROWS FETCH NEXT " + toStr(statement.getFirst(), assist, invokerList) + " ROWS ONLY");
        } else {
            if (statement.getSecond() != null) {
                builder.append(" OFFSET " + toStr(statement.getFirst(), assist, invokerList));
                builder.append(" ROWS FETCH NEXT " + toStr(statement.getSecond(), assist, invokerList) + " ROWS ONLY");
            } else {
                builder.append(" OFFSET 0");
                builder.append(" ROWS FETCH NEXT " + toStr(statement.getFirst(), assist, invokerList) + " ROWS ONLY");
            }
        }
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.CharLengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2) {
            return super.toString(statement, assist, invokerList);
        } else {
            ListColumnStatement listColumnStatement = new ListColumnStatement("||");
            int i;
            for (i = 0; i <= columnList.length - 1; i++) {
                listColumnStatement.add(columnList[i]);
            }
            FunctionStatement.ReturnParameterStatement returnParameterStatement = new FunctionStatement.ReturnParameterStatement();
            returnParameterStatement.setParamsStatement(listColumnStatement);
            return toStr(returnParameterStatement, assist, invokerList);
        }
    }

    @Override
    protected String toString(FunctionStatement.ConcatWsStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String link = toStr(columnList[0], assist, invokerList);
        int i;
        ListColumnStatement listColumnStatement = new ListColumnStatement("||" + link + "||");
        for (i = 1; i <= columnList.length - 1; i++) {
            listColumnStatement.add(columnList[i]);
        }
        FunctionStatement.ReturnParameterStatement returnParameterStatement = new FunctionStatement.ReturnParameterStatement();
        returnParameterStatement.setParamsStatement(listColumnStatement);
        return toStr(returnParameterStatement, assist, invokerList);
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
    protected String toString(FunctionStatement.LeftStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",1," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LPAD(' '," + toStr(statement.getParamsStatement(), assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.PiStatement statement, Assist assist, List<Invoker> invokerList) {
        return "3.14159265358979";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RandStatement statement, Assist assist, List<Invoker> invokerList) {
        return "DBMS_RANDOM.VALUE";
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
        if (!(numStatement instanceof SymbolStatement.StrStatement)) {
            res = "'" + res + "'";
        }
        return "INTERVAL " + res + " YEAR";
    }

    @Override
    protected String toString(IntervalStatement.QuarterIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + Integer.parseInt(res) * 3 + "' MONTH";
    }

    @Override
    protected String toString(IntervalStatement.MonthIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (!(numStatement instanceof SymbolStatement.StrStatement)) {
            res = "'" + res + "'";
        }
        return "INTERVAL " + res + " MONTH";
    }

    @Override
    protected String toString(IntervalStatement.WeekIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (numStatement instanceof SymbolStatement.StrStatement) {
            res = res.substring(1, res.length() - 1);
        }
        return "INTERVAL '" + Integer.parseInt(res) * 7 + "' DAY";
    }

    @Override
    protected String toString(IntervalStatement.DayIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (!(numStatement instanceof SymbolStatement.StrStatement)) {
            res = "'" + res + "'";
        }
        return "INTERVAL " + res + " DAY";
    }

    @Override
    protected String toString(IntervalStatement.HourIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (!(numStatement instanceof SymbolStatement.StrStatement)) {
            res = "'" + res + "'";
        }
        return "INTERVAL " + res + " HOUR";
    }

    @Override
    protected String toString(IntervalStatement.MinuteIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (!(numStatement instanceof SymbolStatement.StrStatement)) {
            res = "'" + res + "'";
        }
        return "INTERVAL " + res + " MINUTE";
    }

    @Override
    protected String toString(IntervalStatement.SecondIntervalStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement numStatement = statement.getStatement();
        String res = toStr(statement.getStatement(), assist, invokerList);
        if (!(numStatement instanceof SymbolStatement.StrStatement)) {
            res = "'" + res + "'";
        }
        return "INTERVAL " + res + " SECOND";
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
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.CurrentDateStatement statement, Assist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.CurTimeStatement statement, Assist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.CurrentTimeStatement statement, Assist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'dd')";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'d')";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'ddd')";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24')";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'mi')";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'mm')";
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
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'q')";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'ss')";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'ww'" + ")";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'yyyy'" + ")";
    }

    @Override
    protected String toString(FunctionStatement.IsNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DECODE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ", null,1,0)";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "NVL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.NullIfStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DECODE(" + toStr(statement.getParamsStatement(), assist, invokerList) + ", null,1)";
    }

    @Override
    protected String toString(FunctionStatement.IfStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String condition;
        if (columnList[0] instanceof ConditionStatement) {
            condition = toStr(columnList[0], assist, invokerList);
        } else {
            condition = "SIGN(" + toStr(columnList[0], assist, invokerList) + ")<>0";
        }
        return "CASE WHEN " + condition + " THEN " + toStr(columnList[1], assist, invokerList) + " ELSE " + toStr(columnList[2], assist, invokerList) + " END";
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
    protected String toString(CaseStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement caseColumn = statement.getCaseColumn();
        if (caseColumn != null) {
            ListColumnStatement whenThenList = (ListColumnStatement) statement.getWhenThenList();
            Statement[] columnList = whenThenList.getColumnList();
            StringJoiner joiner = new StringJoiner(",");
            joiner.add(toStr(caseColumn, assist, invokerList));
            for (Statement column : columnList) {
                CaseStatement.WhenThenStatement whenThenStatement = (CaseStatement.WhenThenStatement) column;
                joiner.add(toStr(whenThenStatement.getWhen(), assist, invokerList) + "," + toStr(whenThenStatement.getThen(), assist, invokerList));
            }
            Statement elseColumn = statement.getElseColumn();
            if (elseColumn != null) {
                joiner.add(toStr(elseColumn, assist, invokerList));
            }
            return "DECODE(" + joiner + ")";
        }
        return super.toString(statement, assist, invokerList);
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

    @Override
    protected String toString(RowNumberStatement.OverStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement orderStatement = statement.getOrderStatement();
        if (orderStatement == null) {
            orderStatement = new OrderStatement();
            ((OrderStatement) orderStatement).setStatement(new SymbolStatement.LetterStatement("null"));
            statement.setOrderStatement(orderStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(AliasStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return toStr(statement.getColumn(), assist, invokerList) + " " + toStr(statement.getAlias(), assist, invokerList);
    }
}
