package com.dream.antlr.sql;

import com.dream.antlr.config.Assist;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;

import java.util.List;

/**
 * sqlserver方言
 */
public class ToSQLServer extends ToPubSQL {

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
        String sql = "STRING_AGG(" + builder + ")";
        if (order != null) {
            sql += "WITHIN GROUP(" + toStr(order, assist, invokerList) + ")";
        }
        return sql;
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
    protected String toString(OperStatement.ADDStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        Statement leftStatement = conditionStatement.getLeft();
        Statement rightStatement = conditionStatement.getRight();
        if (rightStatement instanceof IntervalStatement) {
            Statement intervalParamstatement = ((IntervalStatement) rightStatement).getStatement();
            String left = toStr(leftStatement, assist, invokerList);
            String right = toStr(intervalParamstatement, assist, invokerList);
            if (intervalParamstatement instanceof SymbolStatement.StrStatement) {
                right = right.substring(1, right.length() - 1);
            }
            switch (rightStatement.getNameId()) {
                case 849271629://YearIntervalStatement
                    return "DATEADD(yy," + right + "," + left + ")";
                case -729227010://QuarterIntervalStatement
                    return "DATEADD(qq," + right + "," + left + ")";
                case 2023754666://MonthIntervalStatement
                    return "DATEADD(mm," + right + "," + left + ")";
                case -266778506://WeekIntervalStatement
                    return "DATEADD(wk," + right + "," + left + ")";
                case 1435635214://DayIntervalStatement
                    return "DATEADD(dd," + right + "," + left + ")";
                case 2048430214://HourIntervalStatement
                    return "DATEADD(hh," + right + "," + left + ")";
                case 2068434518://MinuteIntervalStatement
                    return "DATEADD(mi," + right + "," + left + ")";
                case -1888568330://SecondIntervalStatement
                    return "DATEADD(ss," + right + "," + left + ")";
                default:
                    break;
            }
        }
        return toStr(leftStatement, assist, invokerList) + "+" + toStr(rightStatement, assist, invokerList);
    }

    @Override
    protected String toString(OperStatement.SUBStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ConditionStatement conditionStatement = (ConditionStatement) statement.getParentStatement();
        Statement leftStatement = conditionStatement.getLeft();
        Statement rightStatement = conditionStatement.getRight();
        if (rightStatement instanceof IntervalStatement) {
            Statement intervalParamstatement = ((IntervalStatement) rightStatement).getStatement();
            String left = toStr(leftStatement, assist, invokerList);
            String right = toStr(intervalParamstatement, assist, invokerList);
            if (intervalParamstatement instanceof SymbolStatement.StrStatement) {
                right = right.substring(1, right.length() - 1);
            }
            right = "-" + right;
            switch (rightStatement.getNameId()) {
                case 849271629://YearIntervalStatement
                    return "DATEADD(yy," + right + "," + left + ")";
                case -729227010://QuarterIntervalStatement
                    return "DATEADD(qq," + right + "," + left + ")";
                case 2023754666://MonthIntervalStatement
                    return "DATEADD(mm," + right + "," + left + ")";
                case -266778506://WeekIntervalStatement
                    return "DATEADD(wk," + right + "," + left + ")";
                case 1435635214://DayIntervalStatement
                    return "DATEADD(dd," + right + "," + left + ")";
                case 2048430214://HourIntervalStatement
                    return "DATEADD(hh," + right + "," + left + ")";
                case 2068434518://MinuteIntervalStatement
                    return "DATEADD(mi," + right + "," + left + ")";
                case -1888568330://SecondIntervalStatement
                    return "DATEADD(ss," + right + "," + left + ")";
                default:
                    break;
            }
        }
        return toStr(leftStatement, assist, invokerList) + "-" + toStr(rightStatement, assist, invokerList);
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
        StringBuilder builder = new StringBuilder();
        if (statement.isOffset()) {
            if (statement.getSecond() != null) {
                builder.append(" OFFSET " + toStr(statement.getSecond(), assist, invokerList));
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
    protected String toString(FunctionStatement.CharLengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LEN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LengthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LEN(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
    protected String toString(FunctionStatement.Log2Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG(2," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.Log10Statement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "LOG10(" + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
            return "ROUND(" + toStr(statement.getParamsStatement(), assist, invokerList) + ",0)";
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
        ListColumnStatement listColumnStatement = (ListColumnStatement) statement.getParamsStatement();
        Statement[] columnList = listColumnStatement.getColumnList();
        Statement rightStatement = columnList[1];
        if (rightStatement instanceof IntervalStatement) {
            Statement intervalParamstatement = ((IntervalStatement) rightStatement).getStatement();
            Statement leftStatement = columnList[0];
            String left = toStr(leftStatement, assist, invokerList);
            String right = toStr(intervalParamstatement, assist, invokerList);
            if (intervalParamstatement instanceof SymbolStatement.StrStatement) {
                right = right.substring(1, right.length() - 1);
            }
            switch (rightStatement.getNameId()) {
                case 849271629://YearIntervalStatement
                    return "DATEADD(yy," + right + "," + left + ")";
                case -729227010://QuarterIntervalStatement
                    return "DATEADD(qq," + right + "," + left + ")";
                case 2023754666://MonthIntervalStatement
                    return "DATEADD(mm," + right + "," + left + ")";
                case -266778506://WeekIntervalStatement
                    return "DATEADD(wk," + right + "," + left + ")";
                case 1435635214://DayIntervalStatement
                    return "DATEADD(dd," + right + "," + left + ")";
                case 2048430214://HourIntervalStatement
                    return "DATEADD(hh," + right + "," + left + ")";
                case 2068434518://MinuteIntervalStatement
                    return "DATEADD(mi," + right + "," + left + ")";
                case -1888568330://SecondIntervalStatement
                    return "DATEADD(ss," + right + "," + left + ")";
                default:
                    break;
            }
        }
        return super.toString(statement, assist, invokerList);
    }

    @Override
    protected String toString(FunctionStatement.DateSubStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement listColumnStatement = (ListColumnStatement) statement.getParamsStatement();
        Statement[] columnList = listColumnStatement.getColumnList();
        Statement rightStatement = columnList[1];
        if (rightStatement instanceof IntervalStatement) {
            Statement intervalParamstatement = ((IntervalStatement) rightStatement).getStatement();
            Statement leftStatement = columnList[0];
            String left = toStr(leftStatement, assist, invokerList);
            String right = toStr(intervalParamstatement, assist, invokerList);
            if (intervalParamstatement instanceof SymbolStatement.StrStatement) {
                right = right.substring(1, right.length() - 1);
            }
            right = "-" + right;
            switch (rightStatement.getNameId()) {
                case 849271629://YearIntervalStatement
                    return "DATEADD(yy," + right + "," + left + ")";
                case -729227010://QuarterIntervalStatement
                    return "DATEADD(qq," + right + "," + left + ")";
                case 2023754666://MonthIntervalStatement
                    return "DATEADD(mm," + right + "," + left + ")";
                case -266778506://WeekIntervalStatement
                    return "DATEADD(wk," + right + "," + left + ")";
                case 1435635214://DayIntervalStatement
                    return "DATEADD(dd," + right + "," + left + ")";
                case 2048430214://HourIntervalStatement
                    return "DATEADD(hh," + right + "," + left + ")";
                case 2068434518://MinuteIntervalStatement
                    return "DATEADD(mi," + right + "," + left + ")";
                case -1888568330://SecondIntervalStatement
                    return "DATEADD(ss," + right + "," + left + ")";
                default:
                    break;
            }
        }
        return super.toString(statement, assist, invokerList);
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
        return "DATEPART(" + "dd," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfWeekStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "w," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.DayOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "dy," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.HourStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "hh," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.MinuteStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "mi," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.LastDayStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String s = toStr(statement.getParamsStatement(), assist, invokerList);
        return "DATEADD(dd,-DAY(" + s + "),DATEADD(m,1," + s + "))";
    }

    @Override
    protected String toString(FunctionStatement.MonthStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "m," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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
        return "DATEPART(" + "q," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.SecondStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "s," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.WeekOfYearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "ww," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
    }

    @Override
    protected String toString(FunctionStatement.YearStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        return "DATEPART(" + "yy," + toStr(statement.getParamsStatement(), assist, invokerList) + ")";
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

    @Override
    protected String toString(DDLCreateStatement.DDLCreateTableStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        String table = ((SymbolStatement) statement.getStatement()).getValue();
        Statement comment = statement.getComment();
        StringBuilder builder = new StringBuilder();
        if (comment != null) {
            builder.append("EXEC sys.sp_addextendedproperty @name = N'MS_Description'," +
                    "@value = N" + toStr(comment, assist, invokerList) + ", @level0type = N'SCHEMA'," +
                    "@level0name = N'dbo', @level1type = N'TABLE'," +
                    "@level1name = N'" + table + "';");
        }
        ListColumnStatement columnDefineList = statement.getColumnDefineList();
        Statement[] columnList = columnDefineList.getColumnList();
        for (Statement ddlDefineStatement : columnList) {
            if (ddlDefineStatement instanceof DDLDefineStatement.DDLColumnDefineStatement) {
                DDLDefineStatement.DDLColumnDefineStatement ddlColumnDefineStatement = (DDLDefineStatement.DDLColumnDefineStatement) ddlDefineStatement;
                Statement columnComment = ddlColumnDefineStatement.getComment();
                if (columnComment != null) {
                    builder.append("EXEC sys.sp_addextendedproperty @name = N'MS_Description'," +
                            "@value = N" + toStr(columnComment, assist, invokerList) + ", @level0type = N'SCHEMA'," +
                            "@level0name = N'dbo', @level1type = N'TABLE'," +
                            "@level1name = N'" + table + "', @level2type = N'COLUMN'," +
                            "@level2name = N'" + ((SymbolStatement) ddlColumnDefineStatement.getColumn()).getValue() + "';");
                }
            }
        }
        return "CREATE TABLE " + toStr(statement.getStatement(), assist, invokerList) + "(" +
                toStr(columnDefineList, assist, invokerList)
                + ");" + builder;
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
            builder.append(" IDENTITY(1,1)");
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

    @Override
    protected String toString(DDLAlterStatement.DDLAlterModifyStatement statement, Assist assist, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement columnTypeParamList = statement.getColumnTypeParamList();
        StringBuilder builder = new StringBuilder();
        if (columnTypeParamList != null) {
            builder.append("(" + toStr(columnTypeParamList, assist, invokerList) + ")");
        }
        Statement defaultValue = statement.getDefaultValue();
        if (!statement.isNullFlag()) {
            builder.append(" NOT NULL");
        }
        if (defaultValue != null) {
            builder.append(" DEFAULT " + toStr(defaultValue, assist, invokerList));
        }
        return "ALTER TABLE " + toStr(statement.getTable(), assist, invokerList) + " ALTER COLUMN " + toStr(statement.getColumn(), assist, invokerList) + " " + statement.getColumnType().name() + builder;
    }
}
