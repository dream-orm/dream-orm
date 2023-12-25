package com.dream.antlr.smt;

public abstract class IntervalStatement extends Statement {
    private Statement statement;

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public IntervalStatement clone() {
        IntervalStatement intervalStatement = (IntervalStatement) super.clone();
        intervalStatement.setStatement(clone(statement));
        return intervalStatement;
    }

    public static class YearIntervalStatement extends IntervalStatement {
    }

    public static class QuarterIntervalStatement extends IntervalStatement {
    }

    public static class MonthIntervalStatement extends IntervalStatement {
    }

    public static class WeekIntervalStatement extends IntervalStatement {
    }

    public static class DayIntervalStatement extends IntervalStatement {
    }

    public static class HourIntervalStatement extends IntervalStatement {
    }

    public static class MinuteIntervalStatement extends IntervalStatement {
    }

    public static class SecondIntervalStatement extends IntervalStatement {
    }
}
