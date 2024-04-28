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
 * 达梦方言
 */
public class ToDM extends ToPubSQL {
    private Map<String, String> replaceMap = new HashMap<>();

    public ToDM() {
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
    protected String toString(FunctionStatement.LocateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        Statement tempStatement = columnList[0];
        columnList[0] = columnList[1];
        columnList[1] = tempStatement;
        return "INSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.StrToDateStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = AntlrUtil.replace(toStr(columnList[1], assist, invokerList), replaceMap);
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
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

        return "INTERVAL '" + Integer.valueOf(res) * 3 + "' MONTH";

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
    protected String toString(FunctionStatement.CharLengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.Log2Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(2," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PowStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "POWER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'ww'" + ")";
    }
}
