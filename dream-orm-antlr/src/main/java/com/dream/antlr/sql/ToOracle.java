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
 * oracle方言
 */
public class ToOracle extends ToPubSQL {
    private Map<String, String> replaceMap = new HashMap<>();

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
    protected String toString(QueryStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return super.toStringForRowNumber(statement, assist, invokerList);
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
    protected String toString(FunctionStatement.LeftStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",1," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LPAD(' '," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.DateDiffStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "CEIL(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'dd'))";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'d'))";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ddd'))";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24'))";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mi'))";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mm'))";
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
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'q'))";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ss'))";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ww'" + "))";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'yyyy'" + "))";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "NVL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
