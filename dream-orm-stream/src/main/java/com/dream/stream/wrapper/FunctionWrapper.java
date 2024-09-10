package com.dream.stream.wrapper;

import com.dream.antlr.smt.FunctionStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

public class FunctionWrapper {
    private final ListColumnStatement columnStatement = new ListColumnStatement(",");

    public FunctionWrapper ascii(String column) {
        return functionWrapper(new FunctionStatement.AsciiStatement(), col(column));
    }

    public FunctionWrapper len(String column) {
        return functionWrapper(new FunctionStatement.CharLengthStatement(), col(column));
    }

    public FunctionWrapper length(String column) {
        return functionWrapper(new FunctionStatement.LengthStatement(), col(column));
    }

    public FunctionWrapper concat(String column, String... columns) {
        Statement[] statements = new Statement[columns.length + 1];
        statements[0] = col(column);
        for (int i = 0; i < columns.length; i++) {
            statements[i + 1] = col(columns[i]);
        }
        return functionWrapper(new FunctionStatement.ConcatStatement(), statements);
    }

    public FunctionWrapper group_concat(String column) {
        return group_concat(column, null);
    }

    public FunctionWrapper group_concat(String column, String split) {
        return group_concat(false, column, split);
    }

    public FunctionWrapper group_concat(boolean distinct, String column) {
        return group_concat(distinct, column, null);
    }

    public FunctionWrapper group_concat(boolean distinct, String column, String split) {
        FunctionStatement.GroupConcatStatement groupConcatStatement = new FunctionStatement.GroupConcatStatement();
        groupConcatStatement.setDistinct(distinct);
        if (split != null && !split.isEmpty()) {
            groupConcatStatement.setSeparator(new SymbolStatement.StrStatement(split));
        }
        return functionWrapper(groupConcatStatement, col(column));
    }

    public FunctionWrapper find_in_set(String column, String strList) {
        return functionWrapper(new FunctionStatement.FindInSetStatement(), col(column), new SymbolStatement.StrStatement(strList));
    }

    public FunctionWrapper coalesce(String column, String... columns) {
        Statement[] statements = new Statement[columns.length + 1];
        statements[0] = col(column);
        for (int i = 0; i < columns.length; i++) {
            statements[i + 1] = col(columns[i]);
        }
        return functionWrapper(new FunctionStatement.CoalesceStatement(), statements);
    }

    public FunctionWrapper concat_ws(String joiner, String column, String... columns) {
        Statement[] statements = new Statement[columns.length + 2];
        statements[0] = new SymbolStatement.StrStatement(joiner);
        statements[1] = col(column);
        for (int i = 0; i < columns.length; i++) {
            statements[i + 2] = col(columns[i]);
        }
        return functionWrapper(new FunctionStatement.ConcatWsStatement(), statements);
    }

    public FunctionWrapper instr(String column, String column2) {
        return functionWrapper(new FunctionStatement.InStrStatement(), col(column), col(column2));
    }

    public FunctionWrapper locate(String column, String column2) {
        return locate(column, column2, null);
    }

    public FunctionWrapper locate(String column, String column2, Object start) {
        FunctionStatement.LocateStatement locateStatement = new FunctionStatement.LocateStatement();
        Statement columnStatement = col(column);
        Statement column2Statement = col(column2);
        if (start != null) {
            return functionWrapper(locateStatement, columnStatement, column2Statement, col(String.valueOf(start)));
        } else {
            return functionWrapper(locateStatement, columnStatement, column2Statement);
        }
    }

    public FunctionWrapper lcase(String column) {
        return functionWrapper(new FunctionStatement.LcaseStatement(), col(column));
    }

    public FunctionWrapper lower(String column) {
        return functionWrapper(new FunctionStatement.LowerStatement(), col(column));
    }

    public FunctionWrapper upper(String column) {
        return functionWrapper(new FunctionStatement.UpperStatement(), col(column));
    }

    public FunctionWrapper left(String column, Object length) {
        return functionWrapper(new FunctionStatement.LeftStatement(), col(column), col(String.valueOf(length)));
    }

    public FunctionWrapper repeat(String column, Object length) {
        return functionWrapper(new FunctionStatement.RepeatStatement(), col(column), col(String.valueOf(length)));
    }

    public FunctionWrapper right(String column, Object length) {
        return functionWrapper(new FunctionStatement.RightStatement(), col(column), col(String.valueOf(length)));
    }

    public FunctionWrapper ltrim(String column) {
        return functionWrapper(new FunctionStatement.LtrimStatement(), col(column));
    }

    public FunctionWrapper rtrim(String column) {
        return functionWrapper(new FunctionStatement.RtrimStatement(), col(column));
    }

    public FunctionWrapper trim(String column) {
        return functionWrapper(new FunctionStatement.TrimStatement(), col(column));
    }

    public FunctionWrapper reverse(String column) {
        return functionWrapper(new FunctionStatement.ReverseStatement(), col(column));
    }

    public FunctionWrapper replace(String column, String oldStr, String newStr) {
        return functionWrapper(new FunctionStatement.ReplaceStatement(), col(column), new SymbolStatement.StrStatement(oldStr), new SymbolStatement.StrStatement(newStr));
    }

    public FunctionWrapper strcmp(String column, String column2) {
        return functionWrapper(new FunctionStatement.StrcmpStatement(), col(column), col(column2));
    }

    public FunctionWrapper substr(String column, Object pos) {
        return substr(column, pos, null);
    }

    public FunctionWrapper substr(String column, Object pos, Object len) {
        FunctionStatement.SubStrStatement subStrStatement = new FunctionStatement.SubStrStatement();
        Statement columnStatement = col(column);
        Statement posStatement = col(String.valueOf(pos));
        if (len != null) {
            return functionWrapper(subStrStatement, columnStatement, posStatement, col(String.valueOf(len)));
        } else {
            return functionWrapper(subStrStatement, columnStatement, posStatement);
        }
    }

    public FunctionWrapper abs(String column) {
        return functionWrapper(new FunctionStatement.AbsStatement(), col(column));
    }

    public FunctionWrapper avg(String column) {
        return avg(false, column);
    }

    public FunctionWrapper avg(boolean distinct, String column) {
        if (distinct) {
            return functionWrapper(new FunctionStatement.AvgStatement(), " ", col("DISTINCT"), col(column));
        } else {
            return functionWrapper(new FunctionStatement.AvgStatement(), col(column));
        }
    }

    public FunctionWrapper sum(String column) {
        return functionWrapper(new FunctionStatement.SumStatement(), col(column));
    }

    public FunctionWrapper sum(boolean distinct, String column) {
        if (distinct) {
            return functionWrapper(new FunctionStatement.SumStatement(), " ", col("DISTINCT"), col(column));
        } else {
            return functionWrapper(new FunctionStatement.SumStatement(), col(column));
        }
    }

    public FunctionWrapper count() {
        return count(null);
    }

    public FunctionWrapper count(String column) {
        return count(false, column);
    }


    public FunctionWrapper count(boolean distinct, String column) {
        if (column == null) {
            column = "*";
        }
        if (distinct) {
            return functionWrapper(new FunctionStatement.CountStatement(), " ", col("DISTINCT"), col(column));
        } else {
            return functionWrapper(new FunctionStatement.CountStatement(), col(column));
        }
    }

    public FunctionWrapper ceil(String column) {
        return functionWrapper(new FunctionStatement.CeilStatement(), col(column));
    }

    public FunctionWrapper ceiling(String column) {
        return functionWrapper(new FunctionStatement.CeilingStatement(), col(column));
    }

    public FunctionWrapper floor(String column) {
        return functionWrapper(new FunctionStatement.FloorStatement(), col(column));
    }

    public FunctionWrapper rand() {
        return functionWrapper(new FunctionStatement.RandStatement());
    }

    public FunctionWrapper pi() {
        return functionWrapper(new FunctionStatement.PiStatement());
    }

    public FunctionWrapper max(String column) {
        return functionWrapper(new FunctionStatement.MaxStatement(), col(column));
    }

    public FunctionWrapper min(String column) {
        return functionWrapper(new FunctionStatement.MinStatement(), col(column));
    }

    public FunctionWrapper sign(String column) {
        return functionWrapper(new FunctionStatement.SignStatement(), col(column));
    }

    public FunctionWrapper truncate(String column, String column2) {
        return functionWrapper(new FunctionStatement.TruncateStatement(), col(column), col(column2));
    }

    public FunctionWrapper round(String column) {
        return round(column, null);
    }

    public FunctionWrapper round(String column, String column2) {
        if (column2 != null) {
            return functionWrapper(new FunctionStatement.RoundStatement(), col(column), col(column2));
        } else {
            return functionWrapper(new FunctionStatement.RoundStatement(), col(column));
        }
    }

    public FunctionWrapper pow(String column, String column2) {
        return functionWrapper(new FunctionStatement.PowStatement(), col(column), col(column2));
    }

    public FunctionWrapper power(String column, String column2) {
        return functionWrapper(new FunctionStatement.PowerStatement(), col(column), col(column2));
    }

    public FunctionWrapper sqrt(String column) {
        return functionWrapper(new FunctionStatement.SqrtStatement(), col(column));
    }

    public FunctionWrapper exp(String column) {
        return functionWrapper(new FunctionStatement.ExpStatement(), col(column));
    }

    public FunctionWrapper mod(String column) {
        return functionWrapper(new FunctionStatement.ModStatement(), col(column));
    }

    public FunctionWrapper ln(String column) {
        return functionWrapper(new FunctionStatement.LnStatement(), col(column));
    }

    public FunctionWrapper log(String column) {
        return log(column, null);
    }

    public FunctionWrapper log(String column, String column2) {
        if (column2 != null) {
            return functionWrapper(new FunctionStatement.LogStatement(), col(column), col(column2));
        } else {
            return functionWrapper(new FunctionStatement.LogStatement(), col(column));
        }
    }

    public FunctionWrapper log2(String column) {
        return functionWrapper(new FunctionStatement.Log2Statement(), col(column));
    }

    public FunctionWrapper log10(String column) {
        return functionWrapper(new FunctionStatement.Log10Statement(), col(column));
    }

    public FunctionWrapper sin(String column) {
        return functionWrapper(new FunctionStatement.SinStatement(), col(column));
    }

    public FunctionWrapper asin(String column) {
        return functionWrapper(new FunctionStatement.AsinStatement(), col(column));
    }

    public FunctionWrapper cos(String column) {
        return functionWrapper(new FunctionStatement.CosStatement(), col(column));
    }

    public FunctionWrapper acos(String column) {
        return functionWrapper(new FunctionStatement.AcosStatement(), col(column));
    }

    public FunctionWrapper tan(String column) {
        return functionWrapper(new FunctionStatement.TanStatement(), col(column));
    }

    public FunctionWrapper atan(String column) {
        return functionWrapper(new FunctionStatement.AtanStatement(), col(column));
    }

    public FunctionWrapper atan2(String column) {
        return functionWrapper(new FunctionStatement.Atan2Statement(), col(column));
    }

    public FunctionWrapper cot(String column) {
        return functionWrapper(new FunctionStatement.CotStatement(), col(column));
    }

    public FunctionWrapper date_format(String column, String format) {
        return functionWrapper(new FunctionStatement.DateForMatStatement(), col(column), new SymbolStatement.StrStatement(format));
    }

    public FunctionWrapper str_to_date(String column, String format) {
        return functionWrapper(new FunctionStatement.StrToDateStatement(), col(column), new SymbolStatement.StrStatement(format));
    }

    public FunctionWrapper curdate() {
        return functionWrapper(new FunctionStatement.CurDateStatement());
    }

    public FunctionWrapper datediff(String column, String format) {
        return functionWrapper(new FunctionStatement.DateDiffStatement(), col(column), col(format));
    }

    public FunctionWrapper year(String column) {
        return functionWrapper(new FunctionStatement.YearStatement(), col(column));
    }

    public FunctionWrapper day(String column) {
        return functionWrapper(new FunctionStatement.DayStatement(), col(column));
    }

    public FunctionWrapper dayofweek(String column) {
        return functionWrapper(new FunctionStatement.DayOfWeekStatement(), col(column));
    }

    public FunctionWrapper dayofyear(String column) {
        return functionWrapper(new FunctionStatement.DayOfYearStatement(), col(column));
    }

    public FunctionWrapper weekofyear(String column) {
        return functionWrapper(new FunctionStatement.WeekOfYearStatement(), col(column));
    }

    public FunctionWrapper hour(String column) {
        return functionWrapper(new FunctionStatement.HourStatement(), col(column));
    }

    public FunctionWrapper last_day(String column) {
        return functionWrapper(new FunctionStatement.LastDayStatement(), col(column));
    }

    public FunctionWrapper minute(String column) {
        return functionWrapper(new FunctionStatement.MinuteStatement(), col(column));
    }

    public FunctionWrapper month(String column) {
        return functionWrapper(new FunctionStatement.MonthStatement(), col(column));
    }

    public FunctionWrapper quarter(String column) {
        return functionWrapper(new FunctionStatement.QuarterStatement(), col(column));
    }

    public FunctionWrapper date(String column) {
        return functionWrapper(new FunctionStatement.DateStatement(), col(column));
    }

    public FunctionWrapper second(String column) {
        return functionWrapper(new FunctionStatement.SecondStatement(), col(column));
    }

    public FunctionWrapper now() {
        return functionWrapper(new FunctionStatement.NowStatement());
    }

    public FunctionWrapper sysdate() {
        return functionWrapper(new FunctionStatement.SysDateStatement());
    }

    public FunctionWrapper ifnull(String column) {
        return functionWrapper(new FunctionStatement.IsNullStatement(), col(column));
    }

    public FunctionWrapper ifnull(String column, String column2) {
        return functionWrapper(new FunctionStatement.IfNullStatement(), col(column), col(column2));
    }

    public FunctionWrapper _if(String condition, String column, String column2) {
        return functionWrapper(new FunctionStatement.IfStatement(), col(condition), col(column), col(column2));
    }

    public FunctionWrapper lpad(String column, Object length, String padStr) {
        return functionWrapper(new FunctionStatement.LpadStatement(), col(column), col(String.valueOf(length)), new SymbolStatement.StrStatement(padStr));
    }

    public FunctionWrapper rpad(String column, Object length, String padStr) {
        return functionWrapper(new FunctionStatement.RpadStatement(), col(column), col(String.valueOf(length)), new SymbolStatement.StrStatement(padStr));
    }

    public FunctionWrapper nullif(String column, String column2) {
        return functionWrapper(new FunctionStatement.NullIfStatement(), col(column), col(column2));
    }

    public FunctionWrapper space(String column) {
        return functionWrapper(new FunctionStatement.SpaceStatement(), col(column));
    }

    public FunctionWrapper unix_timestamp(String column) {
        return functionWrapper(new FunctionStatement.UnixTimeStampStatement(), col(column));
    }

    public FunctionWrapper from_unixtime(String column) {
        return functionWrapper(new FunctionStatement.FromUnixTimeStatement(), col(column));
    }

    public FunctionWrapper to_char(String column) {
        return functionWrapper(new FunctionStatement.ToCharStatement(), col(column));
    }

    public FunctionWrapper to_number(String column) {
        return functionWrapper(new FunctionStatement.ToNumberStatement(), col(column));
    }

    public FunctionWrapper to_date(String column, String column2) {
        return functionWrapper(new FunctionStatement.ToDateStatement(), col(column), col(column2));
    }

    public FunctionWrapper to_timestamp(String column, String column2) {
        return functionWrapper(new FunctionStatement.ToTimeStampStatement(), col(column), col(column2));
    }

    protected Statement col(String column) {
        return new SymbolStatement.LetterStatement(column);
    }

    protected FunctionWrapper functionWrapper(FunctionStatement functionStatement, Statement... statements) {
        return functionWrapper(functionStatement, ",", statements);
    }

    protected FunctionWrapper functionWrapper(FunctionStatement functionStatement, String split, Statement... statements) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(split);
        listColumnStatement.add(statements);
        functionStatement.setParamsStatement(listColumnStatement);
        return wrap(functionStatement);
    }

    public FunctionWrapper wrap(String column) {
        return wrap(col(column));
    }

    public FunctionWrapper wrap(Statement statement) {
        this.columnStatement.add(statement);
        return this;
    }

    public ListColumnStatement getColumnStatement() {
        return columnStatement;
    }
}
