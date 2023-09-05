package com.dream.flex.def;

import com.dream.antlr.smt.*;
import com.dream.flex.config.DataType;
import com.dream.flex.config.DateType;
import com.dream.flex.factory.DefaultDeleteCreatorFactory;
import com.dream.flex.factory.DefaultInsertCreatorFactory;
import com.dream.flex.factory.DefaultQueryCreatorFactory;
import com.dream.flex.factory.DefaultUpdateCreatorFactory;


public class FunctionDef {

    public static ColumnDef ascii(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AsciiStatement(), columnDef);
    }

    public static ColumnDef len(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CharLengthStatement(), columnDef);
    }

    public static ColumnDef length(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LengthStatement(), columnDef);
    }

    public static ColumnDef concat(ColumnDef columnDef, ColumnDef... columnDefList) {
        if (columnDefList == null) {
            columnDefList = new ColumnDef[0];
        }
        ColumnDef[] columnDefs = new ColumnDef[columnDefList.length + 1];
        columnDefs[0] = columnDef;
        System.arraycopy(columnDefList, 0, columnDefs, 1, columnDefList.length);
        return functionDef(new FunctionStatement.ConcatStatement(), columnDefs);
    }

    public static ColumnDef group_concat(ColumnDef columnDef) {
        return group_concat(false, columnDef);
    }

    public static ColumnDef group_concat(boolean distinct, ColumnDef columnDef) {
        return group_concat(distinct, columnDef, null);
    }

    public static ColumnDef group_concat(ColumnDef columnDef, String split) {
        return group_concat(false, columnDef, split);
    }

    public static ColumnDef group_concat(ColumnDef columnDef, SortDef... sortDefs) {
        return group_concat(false, columnDef, null, sortDefs);
    }

    public static ColumnDef group_concat(boolean distinct, ColumnDef columnDef, SortDef... sortDefs) {
        return group_concat(distinct, columnDef, null, sortDefs);
    }

    public static ColumnDef group_concat(boolean distinct, ColumnDef columnDef, String split, SortDef... sortDefs) {
        return group_concat(distinct, new ColumnDef[]{columnDef}, sortDefs, split);
    }

    public static ColumnDef group_concat(boolean distinct, ColumnDef[] columnDefs, SortDef[] sortDefs, String split) {
        FunctionStatement.GroupConcatStatement groupConcatStatement = new FunctionStatement.GroupConcatStatement();
        if (sortDefs != null && sortDefs.length > 0) {
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            for (SortDef sortDef : sortDefs) {
                listColumnStatement.add(sortDef.getStatement());
            }
            OrderStatement orderStatement = new OrderStatement();
            orderStatement.setOrder(listColumnStatement);
            groupConcatStatement.setOrder(orderStatement);
        }
        groupConcatStatement.setDistinct(distinct);
        if (split != null && !split.isEmpty()) {
            groupConcatStatement.setSeparator(new SymbolStatement.StrStatement(split));
        }
        return functionDef(groupConcatStatement, columnDefs);
    }

    public static ColumnDef find_in_set(ColumnDef columnDef, String strList) {
        return find_in_set(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(strList)));
    }

    public static ColumnDef find_in_set(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.FindInSetStatement(), columnDef, columnDef2);
    }

    public static ColumnDef coalesce(ColumnDef columnDef, ColumnDef... columnDefList) {
        if (columnDefList == null) {
            columnDefList = new ColumnDef[0];
        }
        ColumnDef[] columnDefs = new ColumnDef[columnDefList.length + 1];
        columnDefs[0] = columnDef;
        System.arraycopy(columnDefList, 0, columnDefs, 1, columnDefList.length);
        return functionDef(new FunctionStatement.CoalesceStatement(), columnDefs);
    }

    public static ColumnDef concat_ws(String joiner, ColumnDef columnDef, ColumnDef... columnDefList) {
        if (columnDefList == null) {
            columnDefList = new ColumnDef[0];
        }
        ColumnDef[] columnDefs = new ColumnDef[columnDefList.length + 2];
        columnDefs[0] = new ColumnDef(new SymbolStatement.StrStatement(joiner));
        columnDefs[1] = columnDef;
        System.arraycopy(columnDefList, 0, columnDefs, 2, columnDefList.length);
        return functionDef(new FunctionStatement.ConcatWsStatement(), columnDefs);
    }

    public static ColumnDef instr(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.InStrStatement(), columnDef, columnDef2);
    }

    public static ColumnDef locate(ColumnDef columnDef, ColumnDef columnDef2) {
        return locate(columnDef, columnDef2, (ColumnDef) null);
    }

    public static ColumnDef locate(ColumnDef columnDef, ColumnDef columnDef2, Object start) {
        ColumnDef startColumnDef = null;
        if (start != null) {
            startColumnDef = new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(start)));
        }
        return locate(columnDef, columnDef2, startColumnDef);
    }

    public static ColumnDef locate(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef startColumnDef) {
        if (startColumnDef != null) {
            return functionDef(new FunctionStatement.LocateStatement(), columnDef, columnDef2, startColumnDef);
        } else {
            return functionDef(new FunctionStatement.LocateStatement(), columnDef, columnDef2);
        }
    }

    public static ColumnDef lcase(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LcaseStatement(), columnDef);
    }

    public static ColumnDef lower(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LowerStatement(), columnDef);
    }

    public static ColumnDef upper(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.UpperStatement(), columnDef);
    }

    public static ColumnDef left(ColumnDef columnDef, Object length) {
        return left(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(length))));
    }

    public static ColumnDef left(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.LeftStatement(), columnDef, columnDef2);
    }

    public static ColumnDef repeat(ColumnDef columnDef, Object time) {
        return repeat(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(time))));
    }

    public static ColumnDef repeat(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.RepeatStatement(), columnDef, columnDef2);
    }

    public static ColumnDef right(ColumnDef columnDef, Object length) {
        return right(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(length))));
    }

    public static ColumnDef right(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.RightStatement(), columnDef, columnDef2);
    }

    public static ColumnDef ltrim(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LtrimStatement(), columnDef);
    }

    public static ColumnDef rtrim(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.RtrimStatement(), columnDef);
    }

    public static ColumnDef trim(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.TrimStatement(), columnDef);
    }

    public static ColumnDef reverse(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ReverseStatement(), columnDef);
    }

    public static ColumnDef replace(ColumnDef columnDef, String oldStr, String newStr) {
        return replace(columnDef, new ColumnDef(new SymbolStatement.StrStatement(oldStr)), new ColumnDef(new SymbolStatement.StrStatement(newStr)));
    }

    public static ColumnDef replace(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef columnDef3) {
        return functionDef(new FunctionStatement.ReplaceStatement(), columnDef, columnDef2, columnDef3);
    }

    public static ColumnDef strcmp(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.StrcmpStatement(), columnDef, columnDef2);
    }

    public static ColumnDef substr(ColumnDef columnDef, Object pos) {
        return substr(columnDef, pos, null);
    }

    public static ColumnDef substr(ColumnDef columnDef, Object pos, Object len) {
        ColumnDef columnDef3 = null;
        if (len != null) {
            columnDef3 = new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(len)));
        }
        return substr(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(pos))), columnDef3);
    }

    public static ColumnDef substr(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef columnDef3) {
        if (columnDef3 != null) {
            return functionDef(new FunctionStatement.SubStrStatement(), columnDef, columnDef2, columnDef3);
        } else {
            return functionDef(new FunctionStatement.SubStrStatement(), columnDef, columnDef2);
        }
    }

    public static ColumnDef abs(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AbsStatement(), columnDef);
    }

    public static ColumnDef avg(ColumnDef columnDef) {
        return avg(false, columnDef);
    }

    public static ColumnDef avg(boolean distinct, ColumnDef columnDef) {
        if (distinct) {
            return functionDef(new FunctionStatement.AvgStatement(), " ", new ColumnDef(new SymbolStatement.LetterStatement("DISTINCT")), columnDef);
        } else {
            return functionDef(new FunctionStatement.AvgStatement(), columnDef);
        }
    }

    public static ColumnDef sum(ColumnDef columnDef) {
        return sum(false, columnDef);
    }

    public static ColumnDef sum(boolean distinct, ColumnDef columnDef) {
        if (distinct) {
            return functionDef(new FunctionStatement.SumStatement(), " ", new ColumnDef(new SymbolStatement.LetterStatement("DISTINCT")), columnDef);
        } else {
            return functionDef(new FunctionStatement.SumStatement(), columnDef);
        }
    }

    public static ColumnDef count() {
        return count(false, null);
    }

    public static ColumnDef count(ColumnDef columnDef) {
        return count(false, columnDef);
    }

    public static ColumnDef count(boolean distinct, ColumnDef columnDef) {
        if (columnDef == null) {
            columnDef = col("*");
        }
        if (distinct) {
            return functionDef(new FunctionStatement.CountStatement(), " ", new ColumnDef(new SymbolStatement.LetterStatement("DISTINCT")), columnDef);
        } else {
            return functionDef(new FunctionStatement.CountStatement(), columnDef);
        }
    }

    public static ColumnDef ceil(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CeilStatement(), columnDef);
    }

    public static ColumnDef ceiling(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CeilingStatement(), columnDef);
    }

    public static ColumnDef floor(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.FloorStatement(), columnDef);
    }

    public static ColumnDef rand() {
        return functionDef(new FunctionStatement.RandStatement());
    }

    public static ColumnDef pi() {
        return functionDef(new FunctionStatement.PiStatement());
    }

    public static ColumnDef max(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MaxStatement(), columnDef);
    }

    public static ColumnDef min(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MinStatement(), columnDef);
    }

    public static ColumnDef sign(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SignStatement(), columnDef);
    }

    public static ColumnDef truncate(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.TruncateStatement(), columnDef, columnDef2);
    }

    public static ColumnDef round(ColumnDef columnDef) {
        return round(columnDef, null);
    }

    public static ColumnDef round(ColumnDef columnDef, ColumnDef columnDef2) {
        if (columnDef2 != null) {
            return functionDef(new FunctionStatement.RoundStatement(), columnDef, columnDef2);
        } else {
            return functionDef(new FunctionStatement.RoundStatement(), columnDef);
        }
    }

    public static ColumnDef pow(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.PowStatement(), columnDef, columnDef2);
    }

    public static ColumnDef power(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.PowerStatement(), columnDef, columnDef2);
    }

    public static ColumnDef sqrt(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SqrtStatement(), columnDef);
    }

    public static ColumnDef exp(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ExpStatement(), columnDef);
    }

    public static ColumnDef mod(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ModStatement(), columnDef, columnDef2);
    }

    public static ColumnDef ln(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LnStatement(), columnDef);
    }

    public static ColumnDef log(ColumnDef columnDef) {
        return log(columnDef, null);
    }

    public static ColumnDef log(ColumnDef columnDef, ColumnDef columnDef2) {
        if (columnDef2 != null) {
            return functionDef(new FunctionStatement.LogStatement(), columnDef, columnDef2);
        } else {
            return functionDef(new FunctionStatement.LogStatement(), columnDef);
        }
    }

    public static ColumnDef log2(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.Log2Statement(), columnDef);
    }

    public static ColumnDef log10(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.Log10Statement(), columnDef);
    }

    public static ColumnDef sin(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SinStatement(), columnDef);
    }

    public static ColumnDef asin(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AsinStatement(), columnDef);
    }

    public static ColumnDef cos(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CosStatement(), columnDef);
    }

    public static ColumnDef acos(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AcosStatement(), columnDef);
    }

    public static ColumnDef tan(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.TanStatement(), columnDef);
    }

    public static ColumnDef atan(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AtanStatement(), columnDef);
    }

    public static ColumnDef atan2(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.Atan2Statement(), columnDef, columnDef2);
    }

    public static ColumnDef cot(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CotStatement(), columnDef);
    }

    public static ColumnDef date_add(ColumnDef columnDef, Object number, DateType dateType) {
        DateOperStatement dateOperStatement = null;
        switch (dateType) {
            case SECOND:
                dateOperStatement = new DateOperStatement.SecondDateAddStatement();
                break;
            case MINUTE:
                dateOperStatement = new DateOperStatement.MinuteDateAddStatement();
                break;
            case HOUR:
                dateOperStatement = new DateOperStatement.HourDateAddStatement();
                break;
            case DAY:
                dateOperStatement = new DateOperStatement.DayDateAddStatement();
                break;
            case WEEK:
                dateOperStatement = new DateOperStatement.WeekDateAddStatement();
                break;
            case MONTH:
                dateOperStatement = new DateOperStatement.MonthDateAddStatement();
                break;
            case QUARTER:
                dateOperStatement = new DateOperStatement.QuarterDateAddStatement();
                break;
            case YEAR:
                dateOperStatement = new DateOperStatement.YearDateAddStatement();
                break;
            default:
                break;
        }
        dateOperStatement.setDate(columnDef.getStatement());
        dateOperStatement.setQty(new SymbolStatement.LetterStatement(String.valueOf(number)));
        return new ColumnDef(dateOperStatement);
    }

    public static ColumnDef date_sub(ColumnDef columnDef, Object number, DateType dateType) {
        DateOperStatement dateOperStatement = null;
        switch (dateType) {
            case SECOND:
                dateOperStatement = new DateOperStatement.SecondDateSubStatement();
                break;
            case MINUTE:
                dateOperStatement = new DateOperStatement.MinuteDateSubStatement();
                break;
            case HOUR:
                dateOperStatement = new DateOperStatement.HourDateSubStatement();
                break;
            case DAY:
                dateOperStatement = new DateOperStatement.DayDateSubStatement();
                break;
            case WEEK:
                dateOperStatement = new DateOperStatement.WeekDateSubStatement();
                break;
            case MONTH:
                dateOperStatement = new DateOperStatement.MonthDateSubStatement();
                break;
            case QUARTER:
                dateOperStatement = new DateOperStatement.QuarterDateSubStatement();
                break;
            case YEAR:
                dateOperStatement = new DateOperStatement.YearDateSubStatement();
                break;
            default:
                break;
        }
        dateOperStatement.setDate(columnDef.getStatement());
        dateOperStatement.setQty(new SymbolStatement.LetterStatement(String.valueOf(number)));
        return new ColumnDef(dateOperStatement);
    }

    public static ColumnDef date_format(ColumnDef columnDef, String format) {
        return date_format(columnDef, new ColumnDef(new SymbolStatement.StrStatement(format)));
    }

    public static ColumnDef date_format(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.DateForMatStatement(), columnDef, columnDef2);
    }

    public static ColumnDef str_to_date(ColumnDef columnDef, String format) {
        return str_to_date(columnDef, new ColumnDef(new SymbolStatement.StrStatement(format)));
    }

    public static ColumnDef str_to_date(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.StrToDateStatement(), columnDef, columnDef2);
    }

    public static ColumnDef curdate() {
        return functionDef(new FunctionStatement.CurDateStatement());
    }

    public static ColumnDef datediff(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.DateDiffStatement(), columnDef, columnDef2);
    }

    public static ColumnDef year(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.YearStatement(), columnDef);
    }

    public static ColumnDef day(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DayStatement(), columnDef);
    }

    public static ColumnDef dayofweek(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DayOfWeekStatement(), columnDef);
    }

    public static ColumnDef dayofyear(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DayOfYearStatement(), columnDef);
    }

    public static ColumnDef weekofyear(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.WeekOfYearStatement(), columnDef);
    }

    public static ColumnDef hour(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.HourStatement(), columnDef);
    }

    public static ColumnDef last_day(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LastDayStatement(), columnDef);
    }

    public static ColumnDef minute(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MinuteStatement(), columnDef);
    }

    public static ColumnDef month(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MonthStatement(), columnDef);
    }

    public static ColumnDef quarter(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.QuarterStatement(), columnDef);
    }

    public static ColumnDef date(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DateStatement(), columnDef);
    }

    public static ColumnDef second(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SecondStatement(), columnDef);
    }

    public static ColumnDef now() {
        return functionDef(new FunctionStatement.NowStatement());
    }

    public static ColumnDef sysdate() {
        return functionDef(new FunctionStatement.SysDateStatement());
    }

    public static ColumnDef isnull(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.IsNullStatement(), columnDef);
    }

    public static ColumnDef ifnull(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.IfNullStatement(), columnDef, columnDef2);
    }

    public static ColumnDef if_(ConditionDef conditionDef, ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.IfStatement(), new ColumnDef(conditionDef.getStatement()), columnDef, columnDef2);
    }

    public static ColumnDef lpad(ColumnDef columnDef, Object length, String padStr) {
        return lpad(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(length))), new ColumnDef(new SymbolStatement.LetterStatement(padStr)));
    }

    public static ColumnDef lpad(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef columnDef3) {
        return functionDef(new FunctionStatement.LpadStatement(), columnDef, columnDef2, columnDef3);
    }

    public static ColumnDef rpad(ColumnDef columnDef, Object length, String padStr) {
        return rpad(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(length))), new ColumnDef(new SymbolStatement.LetterStatement(padStr)));
    }

    public static ColumnDef rpad(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef columnDef3) {
        return functionDef(new FunctionStatement.RpadStatement(), columnDef, columnDef2, columnDef3);
    }

    public static ColumnDef nullif(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.NullIfStatement(), columnDef, columnDef2);
    }

    public static ColumnDef convert(ColumnDef columnDef, DataType dataType) {
        ConvertTypeStatement convertTypeStatement = null;
        switch (dataType) {
            case CHAR:
                convertTypeStatement = new ConvertTypeStatement.CharConvertStatement(columnDef.getStatement());
                break;
            case SIGNED:
                convertTypeStatement = new ConvertTypeStatement.SignedConvertStatement(columnDef.getStatement());
                break;
            case FLOAT:
                convertTypeStatement = new ConvertTypeStatement.FloatConvertStatement(columnDef.getStatement());
                break;
            case DECIMAL:
                convertTypeStatement = new ConvertTypeStatement.DecimalConvertStatement(columnDef.getStatement());
                break;
            case TIME:
                convertTypeStatement = new ConvertTypeStatement.TimeConvertStatement(columnDef.getStatement());
                break;
            case DATE:
                convertTypeStatement = new ConvertTypeStatement.DateConvertStatement(columnDef.getStatement());
                break;
            case DATETIME:
                convertTypeStatement = new ConvertTypeStatement.DateTimeConvertStatement(columnDef.getStatement());
                break;
            default:
                break;
        }
        return new ColumnDef(convertTypeStatement);
    }

    public static ColumnDef cast(ColumnDef columnDef, DataType dataType) {
        CastTypeStatement castTypeStatement = null;
        switch (dataType) {
            case CHAR:
                castTypeStatement = new CastTypeStatement.CharCastStatement(columnDef.getStatement());
                break;
            case SIGNED:
                castTypeStatement = new CastTypeStatement.SignedCastStatement(columnDef.getStatement());
                break;
            case FLOAT:
                castTypeStatement = new CastTypeStatement.FloatCastStatement(columnDef.getStatement());
                break;
            case DECIMAL:
                castTypeStatement = new CastTypeStatement.DecimalCastStatement(columnDef.getStatement());
                break;
            case TIME:
                castTypeStatement = new CastTypeStatement.TimeCastStatement(columnDef.getStatement());
                break;
            case DATE:
                castTypeStatement = new CastTypeStatement.DateCastStatement(columnDef.getStatement());
                break;
            case DATETIME:
                castTypeStatement = new CastTypeStatement.DateTimeCastStatement(columnDef.getStatement());
                break;
            default:
                break;
        }
        return new ColumnDef(castTypeStatement);
    }

    public static ColumnDef space(Object length) {
        return space(new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(length))));
    }

    public static ColumnDef space(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SpaceStatement(), columnDef);
    }

    public static ColumnDef unix_timestamp(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.UnixTimeStampStatement(), columnDef);
    }

    public static ColumnDef from_unixtime(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.FromUnixTimeStatement(), columnDef);
    }

    public static ColumnDef to_char(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ToCharStatement(), columnDef);
    }

    public static ColumnDef to_char(ColumnDef columnDef, String format) {
        return to_char(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(format)));
    }

    public static ColumnDef to_char(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ToCharStatement(), columnDef, columnDef2);
    }

    public static ColumnDef to_number(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ToNumberStatement(), columnDef);
    }

    public static ColumnDef to_date(ColumnDef columnDef, String format) {
        return to_date(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(format)));
    }

    public static ColumnDef to_date(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ToDateStatement(), columnDef, columnDef2);
    }

    public static ColumnDef to_timestamp(ColumnDef columnDef, String format) {
        return to_timestamp(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(format)));
    }

    public static ColumnDef to_timestamp(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ToTimeStampStatement(), columnDef, columnDef2);
    }

    public static CaseConditionDef.Builder case_() {
        return new CaseConditionDef.Builder();
    }

    public static CaseColumnDef.Builder case_(ColumnDef columnDef) {
        return new CaseColumnDef.Builder(columnDef);
    }

    public static FromDef select(ColumnDef... columnDefs) {
        return new DefaultQueryCreatorFactory().newSelectDef().select(columnDefs);
    }

    public static UpdateColumnDef update(TableDef tableDef) {
        return new DefaultUpdateCreatorFactory().newUpdateTableDef().update(tableDef);
    }

    public static DeleteWhereDef delete(TableDef tableDef) {
        return new DefaultDeleteCreatorFactory().newDeleteTableDef().delete(tableDef);
    }

    public static InsertIntoColumnsDef insertInto(TableDef tableDef) {
        return new DefaultInsertCreatorFactory().newInsertIntoTableDef().insertInto(tableDef);
    }

    public static ColumnDef col(Object column) {
        return new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(column)));
    }

    public static ColumnDef[] cols(Object[] columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnDefs[i] = col(columns[i]);
        }
        return columnDefs;
    }

    public static ColumnDef column(Object column) {
        return new ColumnDef(new SymbolStatement.SingleMarkStatement(String.valueOf(column)));
    }

    public static ColumnDef[] columns(Object[] columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnDefs[i] = column(columns[i]);
        }
        return columnDefs;
    }

    public static AliasTableDef tab(String table) {
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(new SymbolStatement.LetterStatement(table));
        return new AliasTableDef(aliasStatement);
    }

    public static AliasTableDef table(String table) {
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(new SymbolStatement.SingleMarkStatement(table));
        return new AliasTableDef(aliasStatement);
    }

    public static AliasTableDef table(QueryDef queryDef) {
        BraceStatement braceStatement = new BraceStatement(queryDef.statement());
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(braceStatement);
        return new AliasTableDef(aliasStatement);
    }

    protected static ColumnDef functionDef(FunctionStatement functionStatement, ColumnDef... columnDefs) {
        return functionDef(functionStatement, ",", columnDefs);
    }

    protected static ColumnDef functionDef(FunctionStatement functionStatement, String split, ColumnDef... columnDefs) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(split);
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.getStatement());
        }
        functionStatement.setParamsStatement(listColumnStatement);
        return new ColumnDef(functionStatement);
    }

    public static ConditionDef exists(QueryDef queryDef) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setOper(new OperStatement.EXISTSStatement());
        conditionStatement.setRight(new BraceStatement(queryDef.statement()));
        return new ConditionDef(conditionStatement);
    }

    public static ConditionDef notExists(QueryDef queryDef) {
        return not(exists(queryDef));
    }

    protected static ConditionDef not(ConditionDef conditionDef) {
        ConditionStatement statement = conditionDef.getStatement();
        Statement left = statement.getLeft();
        statement.setLeft(null);
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(left);
        conditionStatement.setOper(new OperStatement.NOTStatement());
        conditionStatement.setRight(statement);
        return new ConditionDef(conditionStatement);
    }
}
