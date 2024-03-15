package com.dream.antlr.smt;

public abstract class FunctionStatement extends Statement {

    protected Statement paramsStatement;

    protected String functionName;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Statement getParamsStatement() {
        return paramsStatement;
    }

    public void setParamsStatement(Statement paramsStatement) {
        this.paramsStatement = wrapParent(paramsStatement);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(paramsStatement);
    }

    @Override
    public FunctionStatement clone() {
        FunctionStatement functionStatement = (FunctionStatement) super.clone();
        functionStatement.setParamsStatement(clone(paramsStatement));
        functionStatement.setFunctionName(functionName);
        return functionStatement;
    }

    public static class AsciiStatement extends FunctionStatement {

    }

    public static class CharLengthStatement extends FunctionStatement {

    }

    public static class LengthStatement extends FunctionStatement {

    }

    public static class ConcatStatement extends FunctionStatement {

    }

    public static class GroupConcatStatement extends FunctionStatement {
        private boolean distinct = false;
        private boolean all = false;
        private Statement order;
        private Statement separator;

        public boolean isDistinct() {
            return distinct;
        }

        public void setDistinct(boolean distinct) {
            this.distinct = distinct;
        }

        public boolean isAll() {
            return all;
        }

        public void setAll(boolean all) {
            this.all = all;
        }

        public Statement getOrder() {
            return order;
        }

        public void setOrder(Statement order) {
            this.order = wrapParent(order);
        }

        public Statement getSeparator() {
            return separator;
        }

        public void setSeparator(Statement separator) {
            this.separator = wrapParent(separator);
        }

        @Override
        public GroupConcatStatement clone() {
            GroupConcatStatement groupConcatStatement = (GroupConcatStatement) super.clone();
            groupConcatStatement.setDistinct(distinct);
            groupConcatStatement.setAll(all);
            groupConcatStatement.setOrder(clone(order));
            groupConcatStatement.setSeparator(clone(separator));
            return groupConcatStatement;
        }
    }

    public static class FindInSetStatement extends FunctionStatement {

    }

    public static class CoalesceStatement extends FunctionStatement {

    }

    public static class ConcatWsStatement extends FunctionStatement {

    }

    public static class InStrStatement extends FunctionStatement {

    }

    public static class LocateStatement extends FunctionStatement {

    }

    public static class LcaseStatement extends FunctionStatement {

    }

    public static class LowerStatement extends FunctionStatement {

    }

    public static class LeftStatement extends FunctionStatement {

    }

    public static class RepeatStatement extends FunctionStatement {

    }

    public static class RightStatement extends FunctionStatement {

    }


    public static class LtrimStatement extends FunctionStatement {

    }


    public static class ReverseStatement extends FunctionStatement {

    }

    public static class ReplaceStatement extends FunctionStatement {

    }

    public static class RtrimStatement extends FunctionStatement {

    }

    public static class StrcmpStatement extends FunctionStatement {

    }

    public static class SubStrStatement extends FunctionStatement {

    }

    public static class TrimStatement extends FunctionStatement {

    }

    public static class UcaseStatement extends FunctionStatement {

    }

    public static class UpperStatement extends FunctionStatement {

    }

    public static class AbsStatement extends FunctionStatement {

    }

    public static class AvgStatement extends FunctionStatement {

    }

    public static class AcosStatement extends FunctionStatement {

    }

    public static class AsinStatement extends FunctionStatement {

    }

    public static class AtanStatement extends FunctionStatement {

    }

    public static class Atan2Statement extends FunctionStatement {

    }

    public static class CeilStatement extends FunctionStatement {

    }

    public static class CeilingStatement extends FunctionStatement {
    }

    public static class CosStatement extends FunctionStatement {

    }

    public static class CotStatement extends FunctionStatement {

    }

    public static class CountStatement extends FunctionStatement {

    }

    public static class ExpStatement extends FunctionStatement {

    }

    public static class FloorStatement extends FunctionStatement {
    }

    public static class LnStatement extends FunctionStatement {
    }

    public static class LogStatement extends FunctionStatement {

    }

    public static class Log2Statement extends FunctionStatement {

    }

    public static class Log10Statement extends FunctionStatement {

    }

    public static class MaxStatement extends FunctionStatement {

    }

    public static class MinStatement extends FunctionStatement {


    }

    public static class ModStatement extends FunctionStatement {


    }

    public static class PiStatement extends FunctionStatement {


    }

    public static class PowStatement extends FunctionStatement {


    }

    public static class PowerStatement extends FunctionStatement {


    }

    public static class RandStatement extends FunctionStatement {


    }

    public static class RoundStatement extends FunctionStatement {


    }

    public static class SignStatement extends FunctionStatement {


    }

    public static class SqrtStatement extends FunctionStatement {


    }

    public static class SumStatement extends FunctionStatement {


    }

    public static class TanStatement extends FunctionStatement {


    }

    public static class TruncateStatement extends FunctionStatement {


    }

    public static class DateAddStatement extends FunctionStatement {


    }

    public static class DateSubStatement extends FunctionStatement {


    }

    public static class DateForMatStatement extends FunctionStatement {
        private String pattern;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public DateForMatStatement clone() {
            DateForMatStatement dateForMatStatement = (DateForMatStatement) super.clone();
            dateForMatStatement.setPattern(pattern);
            return dateForMatStatement;
        }
    }

    public static class ExtractStatement extends FunctionStatement {
        private Statement statement;

        public Statement getStatement() {
            return statement;
        }

        public void setStatement(Statement statement) {
            this.statement = wrapParent(statement);
        }

        @Override
        public ExtractStatement clone() {
            ExtractStatement extractStatement = (ExtractStatement) super.clone();
            extractStatement.setStatement(clone(statement));
            return extractStatement;
        }

        public static class YearExtractStatement extends ExtractStatement {

        }

        public static class QuarterExtractStatement extends ExtractStatement {

        }

        public static class MonthExtractStatement extends ExtractStatement {

        }

        public static class WeekExtractStatement extends ExtractStatement {

        }

        public static class DayExtractStatement extends ExtractStatement {

        }

        public static class HourExtractStatement extends ExtractStatement {

        }

        public static class MinuteExtractStatement extends ExtractStatement {

        }

        public static class SecondExtractStatement extends ExtractStatement {

        }
    }

    public static class StrToDateStatement extends FunctionStatement {
        private String pattern;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public StrToDateStatement clone() {
            StrToDateStatement strToDateStatement = (StrToDateStatement) super.clone();
            strToDateStatement.setPattern(pattern);
            return strToDateStatement;
        }
    }

    public static class CurDateStatement extends FunctionStatement {

    }

    public static class CurrentDateStatement extends FunctionStatement {

    }

    public static class DateDiffStatement extends FunctionStatement {

    }

    public static class DayStatement extends FunctionStatement {

    }


    public static class DayOfWeekStatement extends FunctionStatement {

    }

    public static class DayOfYearStatement extends FunctionStatement {

    }

    public static class HourStatement extends FunctionStatement {

    }

    public static class LastDayStatement extends FunctionStatement {

    }

    public static class MinuteStatement extends FunctionStatement {

    }

    public static class MonthStatement extends FunctionStatement {
    }


    public static class DateStatement extends FunctionStatement {

    }

    public static class NowStatement extends FunctionStatement {

    }

    public static class SysDateStatement extends FunctionStatement {

    }

    public static class QuarterStatement extends FunctionStatement {

    }

    public static class SecondStatement extends FunctionStatement {

    }

    public static class WeekOfYearStatement extends FunctionStatement {

    }

    public static class YearStatement extends FunctionStatement {

    }


    public static class IfNullStatement extends FunctionStatement {

    }

    public static class IfStatement extends FunctionStatement {

    }

    public static class IsNullStatement extends FunctionStatement {

    }

    public static class LpadStatement extends FunctionStatement {
    }

    public static class RpadStatement extends FunctionStatement {
    }

    public static class NullIfStatement extends FunctionStatement {
    }

    public static class ConvertStatement extends FunctionStatement {
    }

    public static class CastStatement extends FunctionStatement {
    }

    public static class SpaceStatement extends FunctionStatement {

    }

    public static class SinStatement extends FunctionStatement {

    }

    public static class DecimalStatement extends FunctionStatement {

    }

    public static class UnixTimeStampStatement extends FunctionStatement {
    }

    public static class FromUnixTimeStatement extends FunctionStatement {
    }

    public static class ToCharStatement extends FunctionStatement {
        private String pattern;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }

    public static class ToNumberStatement extends FunctionStatement {
    }

    public static class ToDateStatement extends FunctionStatement {
        private String pattern;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }

    public static class ToTimeStampStatement extends FunctionStatement {
        private String pattern;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }

    /**
     * 特殊的函数，目的是把concat(a,b,c,...,d)形式，在oracle环境下翻译成a||b||c||...||d用函数表示
     */
    public static class ReturnParameterStatement extends FunctionStatement {

    }
}
