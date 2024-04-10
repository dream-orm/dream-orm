package com.dream.flex.def;

import com.dream.antlr.smt.*;
import com.dream.flex.config.DataType;
import com.dream.flex.config.DateType;
import com.dream.flex.factory.DefaultFlexDeleteFactory;
import com.dream.flex.factory.DefaultFlexInsertFactory;
import com.dream.flex.factory.DefaultFlexQueryFactory;
import com.dream.flex.factory.DefaultFlexUpdateFactory;


public class FunctionDef {
    public static ColumnDef ascii(String column) {
        return ascii(column(column));
    }

    public static ColumnDef ascii(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AsciiStatement(), columnDef);
    }

    public static ColumnDef len(String column) {
        return len(column(column));
    }

    public static ColumnDef len(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CharLengthStatement(), columnDef);
    }

    public static ColumnDef length(String column) {
        return length(column(column));
    }

    public static ColumnDef length(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LengthStatement(), columnDef);
    }

    public static ColumnDef concat(String column, String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        if (columns.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                columnDefs[i] = column(columns[i]);
            }
        }
        return concat(column(column), columnDefs);
    }

    public static ColumnDef concat(ColumnDef columnDef, ColumnDef... columnDefList) {
        ColumnDef[] columnDefs = new ColumnDef[columnDefList.length + 1];
        columnDefs[0] = columnDef;
        System.arraycopy(columnDefList, 0, columnDefs, 1, columnDefList.length);
        return functionDef(new FunctionStatement.ConcatStatement(), columnDefs);
    }

    public static ColumnDef group_concat(String column) {
        return group_concat(column(column));
    }

    public static ColumnDef group_concat(ColumnDef columnDef) {
        return group_concat(false, columnDef);
    }

    public static ColumnDef group_concat(boolean distinct, String column) {
        return group_concat(distinct, column(column));
    }

    public static ColumnDef group_concat(boolean distinct, ColumnDef columnDef) {
        return group_concat(distinct, columnDef, null);
    }

    public static ColumnDef group_concat(String column, String split) {
        return group_concat(column(column), split);
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
            orderStatement.setStatement(listColumnStatement);
            groupConcatStatement.setOrder(orderStatement);
        }
        groupConcatStatement.setDistinct(distinct);
        if (split != null && !split.isEmpty()) {
            groupConcatStatement.setSeparator(new SymbolStatement.StrStatement(split));
        }
        return functionDef(groupConcatStatement, columnDefs);
    }

    public static ColumnDef find_in_set(String column, String strList) {
        return find_in_set(column(column), strList);
    }

    public static ColumnDef find_in_set(ColumnDef columnDef, String strList) {
        return find_in_set(columnDef, new ColumnDef(new SymbolStatement.StrStatement(strList)));
    }

    public static ColumnDef find_in_set(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.FindInSetStatement(), columnDef, columnDef2);
    }

    public static ColumnDef coalesce(String column, String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        if (columns.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                columnDefs[i] = column(columns[i]);
            }
        }
        return coalesce(column(column), columnDefs);
    }

    public static ColumnDef coalesce(ColumnDef columnDef, ColumnDef... columnDefList) {
        ColumnDef[] columnDefs = new ColumnDef[columnDefList.length + 1];
        columnDefs[0] = columnDef;
        System.arraycopy(columnDefList, 0, columnDefs, 1, columnDefList.length);
        return functionDef(new FunctionStatement.CoalesceStatement(), columnDefs);
    }

    public static ColumnDef concat_ws(String joiner, String column, String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        if (columns.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                columnDefs[i] = column(columns[i]);
            }
        }
        return concat_ws(joiner, column(column), columnDefs);
    }

    public static ColumnDef concat_ws(String joiner, ColumnDef columnDef, ColumnDef... columnDefList) {
        ColumnDef[] columnDefs = new ColumnDef[columnDefList.length + 2];
        columnDefs[0] = new ColumnDef(new SymbolStatement.StrStatement(joiner));
        columnDefs[1] = columnDef;
        System.arraycopy(columnDefList, 0, columnDefs, 2, columnDefList.length);
        return functionDef(new FunctionStatement.ConcatWsStatement(), columnDefs);
    }

    public static ColumnDef instr(String column, String column2) {
        return instr(column(column), column(column2));
    }

    public static ColumnDef instr(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.InStrStatement(), columnDef, columnDef2);
    }

    public static ColumnDef locate(String column, String column2) {
        return locate(column(column), column(column2));
    }

    public static ColumnDef locate(ColumnDef columnDef, ColumnDef columnDef2) {
        return locate(columnDef, columnDef2, null);
    }

    public static ColumnDef locate(String column, String column2, Object start) {
        return locate(column(column), column(column2), start);
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

    public static ColumnDef lcase(String column) {
        return lcase(column(column));
    }

    public static ColumnDef lcase(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LcaseStatement(), columnDef);
    }

    public static ColumnDef lower(String column) {
        return lower(column(column));
    }

    public static ColumnDef lower(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LowerStatement(), columnDef);
    }

    public static ColumnDef upper(String column) {
        return upper(column(column));
    }

    public static ColumnDef upper(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.UpperStatement(), columnDef);
    }

    public static ColumnDef left(String column, Object length) {
        return left(column(column), length);
    }

    public static ColumnDef left(ColumnDef columnDef, Object length) {
        return left(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(length))));
    }

    public static ColumnDef left(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.LeftStatement(), columnDef, columnDef2);
    }

    public static ColumnDef repeat(String column, Object time) {
        return repeat(column(column), time);
    }

    public static ColumnDef repeat(ColumnDef columnDef, Object time) {
        return repeat(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(time))));
    }

    public static ColumnDef repeat(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.RepeatStatement(), columnDef, columnDef2);
    }

    public static ColumnDef right(String column, Object length) {
        return right(column(column), length);
    }

    public static ColumnDef right(ColumnDef columnDef, Object length) {
        return right(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(length))));
    }

    public static ColumnDef right(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.RightStatement(), columnDef, columnDef2);
    }

    public static ColumnDef ltrim(String column) {
        return ltrim(column(column));
    }

    public static ColumnDef ltrim(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LtrimStatement(), columnDef);
    }

    public static ColumnDef rtrim(String column) {
        return rtrim(column(column));
    }

    public static ColumnDef rtrim(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.RtrimStatement(), columnDef);
    }

    public static ColumnDef trim(String column) {
        return trim(column(column));
    }

    public static ColumnDef trim(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.TrimStatement(), columnDef);
    }

    public static ColumnDef reverse(String column) {
        return reverse(column(column));
    }

    public static ColumnDef reverse(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ReverseStatement(), columnDef);
    }

    public static ColumnDef replace(String column, String oldStr, String newStr) {
        return replace(column(column), oldStr, newStr);
    }

    public static ColumnDef replace(ColumnDef columnDef, String oldStr, String newStr) {
        return replace(columnDef, new ColumnDef(new SymbolStatement.StrStatement(oldStr)), new ColumnDef(new SymbolStatement.StrStatement(newStr)));
    }

    public static ColumnDef replace(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef columnDef3) {
        return functionDef(new FunctionStatement.ReplaceStatement(), columnDef, columnDef2, columnDef3);
    }

    public static ColumnDef strcmp(String column, String column2) {
        return strcmp(column(column), column(column2));
    }

    public static ColumnDef strcmp(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.StrcmpStatement(), columnDef, columnDef2);
    }

    public static ColumnDef substr(String column, Object pos) {
        return substr(column(column), pos);
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

    public static ColumnDef abs(String column) {
        return abs(column(column));
    }

    public static ColumnDef abs(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AbsStatement(), columnDef);
    }

    public static ColumnDef avg(String column) {
        return avg(column(column));
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

    public static ColumnDef sum(String column) {
        return sum(column(column));
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
        return count(false, (ColumnDef) null);
    }

    public static ColumnDef count(String column) {
        return count(column(column));
    }

    public static ColumnDef count(ColumnDef columnDef) {
        return count(false, columnDef);
    }

    public static ColumnDef count(boolean distinct, String column) {
        return count(distinct, column(column));
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

    public static ColumnDef ceil(String column) {
        return ceil(column(column));
    }

    public static ColumnDef ceil(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CeilStatement(), columnDef);
    }

    public static ColumnDef ceiling(String column) {
        return ceiling(column(column));
    }

    public static ColumnDef ceiling(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CeilingStatement(), columnDef);
    }

    public static ColumnDef floor(String column) {
        return floor(column(column));
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

    public static ColumnDef max(String column) {
        return max(column(column));
    }

    public static ColumnDef max(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MaxStatement(), columnDef);
    }

    public static ColumnDef min(String column) {
        return min(column(column));
    }

    public static ColumnDef min(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MinStatement(), columnDef);
    }

    public static ColumnDef sign(String column) {
        return sign(column(column));
    }

    public static ColumnDef sign(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SignStatement(), columnDef);
    }

    public static ColumnDef truncate(String column, String column2) {
        return truncate(column(column), column(column2));
    }

    public static ColumnDef truncate(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.TruncateStatement(), columnDef, columnDef2);
    }

    public static ColumnDef round(String column) {
        return round(column(column));
    }

    public static ColumnDef round(ColumnDef columnDef) {
        return round(columnDef, null);
    }

    public static ColumnDef round(String column, String column2) {
        return round(column(column), column(column2));
    }

    public static ColumnDef round(ColumnDef columnDef, ColumnDef columnDef2) {
        if (columnDef2 != null) {
            return functionDef(new FunctionStatement.RoundStatement(), columnDef, columnDef2);
        } else {
            return functionDef(new FunctionStatement.RoundStatement(), columnDef);
        }
    }

    public static ColumnDef pow(String column, String column2) {
        return pow(column(column), column(column2));
    }

    public static ColumnDef pow(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.PowStatement(), columnDef, columnDef2);
    }

    public static ColumnDef power(String column, String column2) {
        return power(column(column), column(column2));
    }

    public static ColumnDef power(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.PowerStatement(), columnDef, columnDef2);
    }

    public static ColumnDef sqrt(String column) {
        return sqrt(column(column));
    }

    public static ColumnDef sqrt(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SqrtStatement(), columnDef);
    }

    public static ColumnDef exp(String column) {
        return exp(column(column));
    }

    public static ColumnDef exp(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ExpStatement(), columnDef);
    }

    public static ColumnDef mod(String column, String column2) {
        return mod(column(column), column(column2));
    }

    public static ColumnDef mod(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ModStatement(), columnDef, columnDef2);
    }

    public static ColumnDef ln(String column) {
        return ln(column(column));
    }

    public static ColumnDef ln(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LnStatement(), columnDef);
    }

    public static ColumnDef log(String column) {
        return log(column(column));
    }

    public static ColumnDef log(ColumnDef columnDef) {
        return log(columnDef, null);
    }

    public static ColumnDef log(String column, String column2) {
        return log(column(column), column(column2));
    }

    public static ColumnDef log(ColumnDef columnDef, ColumnDef columnDef2) {
        if (columnDef2 != null) {
            return functionDef(new FunctionStatement.LogStatement(), columnDef, columnDef2);
        } else {
            return functionDef(new FunctionStatement.LogStatement(), columnDef);
        }
    }

    public static ColumnDef log2(String column) {
        return log2(column(column));
    }

    public static ColumnDef log2(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.Log2Statement(), columnDef);
    }

    public static ColumnDef log10(String column) {
        return log10(column(column));
    }

    public static ColumnDef log10(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.Log10Statement(), columnDef);
    }

    public static ColumnDef sin(String column) {
        return sin(column(column));
    }

    public static ColumnDef sin(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SinStatement(), columnDef);
    }

    public static ColumnDef asin(String column) {
        return asin(column(column));
    }

    public static ColumnDef asin(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AsinStatement(), columnDef);
    }

    public static ColumnDef cos(String column) {
        return cos(column(column));
    }

    public static ColumnDef cos(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CosStatement(), columnDef);
    }

    public static ColumnDef acos(String column) {
        return acos(column(column));
    }

    public static ColumnDef acos(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AcosStatement(), columnDef);
    }

    public static ColumnDef tan(String column) {
        return tan(column(column));
    }

    public static ColumnDef tan(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.TanStatement(), columnDef);
    }

    public static ColumnDef atan(String column) {
        return atan(column(column));
    }

    public static ColumnDef atan(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AtanStatement(), columnDef);
    }

    public static ColumnDef atan2(String column, String column2) {
        return atan2(column(column), column(column2));
    }

    public static ColumnDef atan2(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.Atan2Statement(), columnDef, columnDef2);
    }

    public static ColumnDef cot(String column) {
        return cot(column(column));
    }

    public static ColumnDef cot(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CotStatement(), columnDef);
    }

    public static ColumnDef date_add(String column, Object number, DateType dateType) {
        return date_add(column(column), number, dateType);
    }

    public static ColumnDef date_add(ColumnDef columnDef, Object number, DateType dateType) {
        IntervalStatement intervalStatement = null;
        switch (dateType) {
            case SECOND:
                intervalStatement = new IntervalStatement.SecondIntervalStatement();
                break;
            case MINUTE:
                intervalStatement = new IntervalStatement.MinuteIntervalStatement();
                break;
            case HOUR:
                intervalStatement = new IntervalStatement.HourIntervalStatement();
                break;
            case DAY:
                intervalStatement = new IntervalStatement.DayIntervalStatement();
                break;
            case WEEK:
                intervalStatement = new IntervalStatement.WeekIntervalStatement();
                break;
            case MONTH:
                intervalStatement = new IntervalStatement.MonthIntervalStatement();
                break;
            case QUARTER:
                intervalStatement = new IntervalStatement.QuarterIntervalStatement();
                break;
            case YEAR:
                intervalStatement = new IntervalStatement.YearIntervalStatement();
                break;
            default:
                break;
        }
        intervalStatement.setStatement(new SymbolStatement.LetterStatement(String.valueOf(number)));
        FunctionStatement.DateAddStatement dateAddStatement = new FunctionStatement.DateAddStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(columnDef.getStatement());
        listColumnStatement.add(intervalStatement);
        dateAddStatement.setParamsStatement(listColumnStatement);
        return new ColumnDef(dateAddStatement);
    }

    public static ColumnDef date_sub(String column, Object number, DateType dateType) {
        return date_sub(column(column), number, dateType);
    }

    public static ColumnDef date_sub(ColumnDef columnDef, Object number, DateType dateType) {
        IntervalStatement intervalStatement = null;
        switch (dateType) {
            case SECOND:
                intervalStatement = new IntervalStatement.SecondIntervalStatement();
                break;
            case MINUTE:
                intervalStatement = new IntervalStatement.MinuteIntervalStatement();
                break;
            case HOUR:
                intervalStatement = new IntervalStatement.HourIntervalStatement();
                break;
            case DAY:
                intervalStatement = new IntervalStatement.DayIntervalStatement();
                break;
            case WEEK:
                intervalStatement = new IntervalStatement.WeekIntervalStatement();
                break;
            case MONTH:
                intervalStatement = new IntervalStatement.MonthIntervalStatement();
                break;
            case QUARTER:
                intervalStatement = new IntervalStatement.QuarterIntervalStatement();
                break;
            case YEAR:
                intervalStatement = new IntervalStatement.YearIntervalStatement();
                break;
            default:
                break;
        }
        intervalStatement.setStatement(new SymbolStatement.LetterStatement(String.valueOf(number)));
        FunctionStatement.DateSubStatement dateSubStatement = new FunctionStatement.DateSubStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(columnDef.getStatement());
        listColumnStatement.add(intervalStatement);
        dateSubStatement.setParamsStatement(listColumnStatement);
        return new ColumnDef(dateSubStatement);
    }

    public static ColumnDef date_format(String column, String format) {
        return date_format(column(column), format);
    }

    public static ColumnDef date_format(ColumnDef columnDef, String format) {
        return date_format(columnDef, new ColumnDef(new SymbolStatement.StrStatement(format)));
    }

    public static ColumnDef date_format(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.DateForMatStatement(), columnDef, columnDef2);
    }

    public static ColumnDef str_to_date(String column, String format) {
        return str_to_date(column(column), format);
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

    public static ColumnDef datediff(String column, String column2) {
        return datediff(column(column), column(column2));
    }

    public static ColumnDef datediff(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.DateDiffStatement(), columnDef, columnDef2);
    }

    public static ColumnDef year(String column) {
        return year(column(column));
    }

    public static ColumnDef year(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.YearStatement(), columnDef);
    }

    public static ColumnDef day(String column) {
        return day(column(column));
    }

    public static ColumnDef day(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DayStatement(), columnDef);
    }

    public static ColumnDef dayofweek(String column) {
        return dayofweek(column(column));
    }

    public static ColumnDef dayofweek(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DayOfWeekStatement(), columnDef);
    }

    public static ColumnDef dayofyear(String column) {
        return dayofyear(column(column));
    }

    public static ColumnDef dayofyear(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DayOfYearStatement(), columnDef);
    }

    public static ColumnDef weekofyear(String column) {
        return weekofyear(column(column));
    }

    public static ColumnDef weekofyear(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.WeekOfYearStatement(), columnDef);
    }

    public static ColumnDef hour(String column) {
        return hour(column(column));
    }

    public static ColumnDef hour(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.HourStatement(), columnDef);
    }

    public static ColumnDef last_day(String column) {
        return last_day(column(column));
    }

    public static ColumnDef last_day(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LastDayStatement(), columnDef);
    }

    public static ColumnDef minute(String column) {
        return minute(column(column));
    }

    public static ColumnDef minute(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MinuteStatement(), columnDef);
    }

    public static ColumnDef month(String column) {
        return month(column(column));
    }

    public static ColumnDef month(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.MonthStatement(), columnDef);
    }

    public static ColumnDef quarter(String column) {
        return quarter(column(column));
    }

    public static ColumnDef quarter(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.QuarterStatement(), columnDef);
    }

    public static ColumnDef date(String column) {
        return date(column(column));
    }

    public static ColumnDef date(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.DateStatement(), columnDef);
    }

    public static ColumnDef second(String column) {
        return second(column(column));
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

    public static ColumnDef isnull(String column) {
        return isnull(column(column));
    }

    public static ColumnDef isnull(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.IsNullStatement(), columnDef);
    }

    public static ColumnDef ifnull(String column, String column2) {
        return ifnull(column(column), column(column2));
    }

    public static ColumnDef ifnull(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.IfNullStatement(), columnDef, columnDef2);
    }

    public static ColumnDef if_(ConditionDef conditionDef, String column, String column2) {
        return if_(conditionDef, column(column), column(column2));
    }

    public static ColumnDef if_(ConditionDef conditionDef, ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.IfStatement(), new ColumnDef(conditionDef.getStatement()), columnDef, columnDef2);
    }

    public static ColumnDef lpad(String column, Object length, String padStr) {
        return lpad(column(column), length, padStr);
    }

    public static ColumnDef lpad(ColumnDef columnDef, Object length, String padStr) {
        return lpad(columnDef, col(length), new ColumnDef(new SymbolStatement.StrStatement(padStr)));
    }

    public static ColumnDef lpad(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef columnDef3) {
        return functionDef(new FunctionStatement.LpadStatement(), columnDef, columnDef2, columnDef3);
    }

    public static ColumnDef rpad(String column, Object length, String padStr) {
        return rpad(column(column), length, padStr);
    }

    public static ColumnDef rpad(ColumnDef columnDef, Object length, String padStr) {
        return rpad(columnDef, col(length), col(padStr));
    }

    public static ColumnDef rpad(ColumnDef columnDef, ColumnDef columnDef2, ColumnDef columnDef3) {
        return functionDef(new FunctionStatement.RpadStatement(), columnDef, columnDef2, columnDef3);
    }

    public static ColumnDef nullif(String column, String column2) {
        return nullif(column(column), column(column2));
    }

    public static ColumnDef nullif(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.NullIfStatement(), columnDef, columnDef2);
    }

    public static ColumnDef convert(String column, DataType dataType) {
        return convert(column(column), dataType);
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

    public static ColumnDef cast(String column, DataType dataType) {
        return cast(column(column), dataType);
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

    public static ColumnDef unix_timestamp(String column) {
        return unix_timestamp(column(column));
    }

    public static ColumnDef unix_timestamp(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.UnixTimeStampStatement(), columnDef);
    }

    public static ColumnDef from_unixtime(String column) {
        return from_unixtime(column(column));
    }

    public static ColumnDef from_unixtime(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.FromUnixTimeStatement(), columnDef);
    }

    public static ColumnDef to_char(String column) {
        return to_char(column(column));
    }

    public static ColumnDef to_char(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ToCharStatement(), columnDef);
    }

    public static ColumnDef to_char(String column, String format) {
        return to_char(column(column), format);
    }

    public static ColumnDef to_char(ColumnDef columnDef, String format) {
        return to_char(columnDef, col(format));
    }

    public static ColumnDef to_char(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ToCharStatement(), columnDef, columnDef2);
    }

    public static ColumnDef to_number(String column) {
        return to_number(column(column));
    }

    public static ColumnDef to_number(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ToNumberStatement(), columnDef);
    }

    public static ColumnDef to_date(String column, String format) {
        return to_date(column(column), format);
    }

    public static ColumnDef to_date(ColumnDef columnDef, String format) {
        return to_date(columnDef, new ColumnDef(new SymbolStatement.LetterStatement(format)));
    }

    public static ColumnDef to_date(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ToDateStatement(), columnDef, columnDef2);
    }

    public static ColumnDef to_timestamp(String column, String format) {
        return to_timestamp(column(column), format);
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

    public static FromDef select() {
        return select(new ColumnDef[0]);
    }

    public static FromDef select(String... columns) {
        ColumnDef[] columnDefs = new ColumnDef[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnDefs[i] = column(columns[i]);
        }
        return select(columnDefs);
    }

    public static FromDef select(ColumnDef... columnDefs) {
        return new DefaultFlexQueryFactory().newSelectDef().select(columnDefs);
    }

    public static UpdateColumnDef update(String table) {
        return update(table(table));
    }

    public static UpdateColumnDef update(TableDef tableDef) {
        return new DefaultFlexUpdateFactory().newUpdateTableDef().update(tableDef);
    }

    public static DeleteWhereDef delete(String table) {
        return delete(table(table));
    }

    public static DeleteWhereDef delete(TableDef tableDef) {
        return new DefaultFlexDeleteFactory().newDeleteTableDef().delete(tableDef);
    }

    public static InsertIntoColumnsDef insertInto(String table) {
        return insertInto(table(table));
    }

    public static InsertIntoColumnsDef insertInto(TableDef tableDef) {
        return new DefaultFlexInsertFactory().newInsertIntoTableDef().insertInto(tableDef);
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

    public static ColumnDef functionDef(FunctionStatement functionStatement, ColumnDef... columnDefs) {
        return functionDef(functionStatement, ",", columnDefs);
    }

    public static ColumnDef functionDef(FunctionStatement functionStatement, String split, ColumnDef... columnDefs) {
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

    public static ConditionDef not(ConditionDef conditionDef) {
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
