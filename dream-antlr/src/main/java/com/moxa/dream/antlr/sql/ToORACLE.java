package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.expr.QueryExpr;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.List;

public class ToORACLE extends ToPubSQL {
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
    protected String toString(FunctionStatement.ToCharStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    protected String toString(FunctionStatement.ToNumberStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RepeatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String tar = toStr(columnList[0], assist, invokerList);
        String num = toStr(columnList[1], assist, invokerList);
        return "LPAD(" + tar + ",LENGTH(" + tar + ")*" + num + "," + tar + ")";
    }

    @Override
    protected String toString(FunctionStatement.InStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "INSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LocateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        Statement tempStatement = columnList[0];
        columnList[0] = columnList[1];
        columnList[1] = tempStatement;
        return "INSTR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + toStr(columnList[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DateForMatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "TO_CHAR(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(FunctionStatement.StrToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String pattern = statement.getPattern();
        if (pattern == null) {
            pattern = getPattern(toStr(columnList[1], assist, invokerList));
            statement.setPattern(pattern);
        }
        return "TO_DATE(" + toStr(columnList[0], assist, invokerList) + "," + pattern + ")";
    }

    @Override
    protected String toString(DateOperStatement.YearDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + "," + type + toStr(statement.getQty(), assist, invokerList) + "*12)";
    }

    @Override
    protected String toString(DateOperStatement.QuarterDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + "," + type + toStr(statement.getQty(), assist, invokerList) + "*3)";
    }

    @Override
    protected String toString(DateOperStatement.MonthDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "";
        if (!statement.isPositive())
            type = "-";
        return "ADD_MONTHS(" + toStr(statement.getDate(), assist, invokerList) + "," + type + toStr(statement.getQty(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(DateOperStatement.WeekDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + toStr(statement.getQty(), assist, invokerList) + "*7";
    }

    @Override
    protected String toString(DateOperStatement.DayDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + toStr(statement.getQty(), assist, invokerList);
    }

    @Override
    protected String toString(DateOperStatement.HourDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + toStr(statement.getQty(), assist, invokerList) + "/24";
    }

    @Override
    protected String toString(DateOperStatement.MinuteDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + toStr(statement.getQty(), assist, invokerList) + "/1440";
    }

    @Override
    protected String toString(DateOperStatement.SecondDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        String type = "+";
        if (!statement.isPositive())
            type = "-";
        return toStr(statement.getDate(), assist, invokerList) + type + toStr(statement.getQty(), assist, invokerList) + "/86400";
    }

    @Override
    protected String toString(CastTypeStatement.SignedCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(CastTypeStatement.CharCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(CastTypeStatement.DateCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new InvokerException("ORACLE不支持CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATE)");
    }

    @Override
    protected String toString(CastTypeStatement.TimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new InvokerException("ORACLE不支持CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIME)");
    }

    @Override
    protected String toString(CastTypeStatement.DateTimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new InvokerException("ORACLE不支持CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATETIME)");
    }

    @Override
    protected String toString(ConvertTypeStatement.DateConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new InvokerException("ORACLE不支持CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATE)");
    }

    @Override
    protected String toString(ConvertTypeStatement.TimeConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new InvokerException("ORACLE不支持CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS TIME)");
    }

    @Override
    protected String toString(ConvertTypeStatement.DateTimeConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new InvokerException("ORACLE不支持CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS DATETIME)");
    }

    @Override
    protected String toString(ConvertTypeStatement.SignedConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CAST(" + toStr(statement.getStatement(), assist, invokerList) + " AS INT)";
    }

    @Override
    protected String toString(ConvertTypeStatement.CharConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_CHAR(" + toStr(statement.getStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(InsertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        if (statement.isAll()) {
            String table = toStr(statement.getTable(), assist, invokerList);
            String param = " ";
            if (statement.getParams() != null) {
                param = toStr(statement.getParams(), assist, invokerList);
            }
            ListColumnStatement listColumnStatement = (ListColumnStatement) (((InsertStatement.ValuesStatement) statement.getValues()).getStatement());
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT ALL");
            for (Statement columnStatement : listColumnStatement.getColumnList()) {
                builder.append(" INTO " + table + param + "VALUES" + toStr(columnStatement, assist, invokerList));
            }
            return builder.toString();
        } else {
            return super.toString(statement, assist, invokerList);
        }
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
                sql = "select* from(select rownum rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn <=" + maxValue;
            } else {
                maxValue = toDREAM.toStr(second, null, null);
                minValue = toDREAM.toStr(first, null, null);
                sql = "select* from(select rownum rn,t_tmp.* from (" + querySql + ")t_tmp)t_tmp where rn between " + minValue + " and " + minValue + "+" + maxValue;
            }
            QueryStatement queryStatement = (QueryStatement) new QueryExpr(new ExprReader(sql)).expr();
            ReflectUtil.copy(statement, queryStatement);
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.LenStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LENGTH(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.ConcatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        if (columnList.length == 2)
            return super.toString(statement, assist, invokerList);
        else {
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            for (i = 0; i < columnList.length - 1; i++) {
                stringBuilder.append(toStr(columnList[i], assist, invokerList) + "||");
            }
            stringBuilder.append(toStr(columnList[i], assist, invokerList));
            return stringBuilder.toString();
        }
    }

    protected String toString(FunctionStatement.ConcatWsStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String link = toStr(columnList[0], assist, invokerList);
        int i;
        StringBuilder builder = new StringBuilder();
        for (i = 1; i < columnList.length - 1; i++) {
            builder.append(toStr(columnList[i], assist, invokerList) + "||" + link + "||");
        }
        builder.append(toStr(columnList[i], assist, invokerList));
        return builder.toString();
    }

    @Override
    protected String toString(FunctionStatement.LeftStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",1," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RightStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "SUBSTR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SpaceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LPAD(' '," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",' ')";
    }

    @Override
    protected String toString(FunctionStatement.LpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.RpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "RPAD(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.CeilingStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEIL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LogStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        if (((ListColumnStatement) statement.getParamsStatement()).getColumnList().length == 1)
            return "LN(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
        else
            return "LOG(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log2Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(2," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LOG(10," + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.PiStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "3.14159265358979";
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
        return "DBMS_RANDOM.VALUE";
    }

    @Override
    protected String toString(FunctionStatement.RoundStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.TruncateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) statement.getParamsStatement()).getColumnList();
        String s1 = toStr(columnList[0], assist, invokerList);
        String s2 = toStr(columnList[1], assist, invokerList);
        return "FLOOR(" + s1 + "*POWER(10," + s2 + "))*POWER(10,-" + s2 + ")";
    }


    @Override
    protected String toString(FunctionStatement.DateAddStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return toStr(statement.getParamsStatement(), assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.CurDateStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.DateDiffStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "CEIL(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + "-" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[1], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'dd'))";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'d'))";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ddd'))";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",'hh24'))";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mi'))";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "LAST_DAY(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'mm'))";
    }

    @Override
    protected String toString(FunctionStatement.DateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        throw new RuntimeException("尚未完成DATE");
    }

    @Override
    protected String toString(FunctionStatement.NowStatement statement, ToAssist assist, List<Invoker> invokerList) {
        return "SYSDATE";
    }

    @Override
    protected String toString(FunctionStatement.QuarterStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'q'))";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ss'))";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'ww'" + "))";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "TO_NUMBER(TO_CHAR(" + toStr(((ListColumnStatement) statement.getParamsStatement()).getColumnList()[0], assist, invokerList) + ",'yyyy'" + "))";
    }

    @Override
    protected String toString(FunctionStatement.IfNullStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "NVL(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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

    @Override
    protected String toString(OperStatement.BITANDStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        return "BITAND(" + toStr(conditionStatement.getLeft(), assist, invokerList) + "," + toStr(conditionStatement.getRight(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(OperStatement.BITORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + right + "-BITAND(" + left + "," + right + ")";
    }

    @Override
    protected String toString(OperStatement.BITXORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        String left = toStr(conditionStatement.getLeft(), assist, invokerList);
        String right = toStr(conditionStatement.getRight(), assist, invokerList);
        return left + right + "-2*BITAND(" + left + "," + right + ")";
    }
}
