package com.moxa.dream.antlr.smt;

public abstract class DateOperStatement extends Statement {
    private Statement date;
    private Statement qty;
    private boolean positive;

    public Statement getDate() {
        return date;
    }

    public void setDate(Statement date) {
        this.date = date;
        if (date != null)
            date.parentStatement = this;
    }

    public Statement getQty() {
        return qty;
    }

    public void setQty(Statement qty) {
        this.qty = qty;
        if (qty != null)
            qty.parentStatement = this;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(date, qty);
    }

    public static class YearDateOperStatement extends DateOperStatement {

    }

    public static class QuarterDateOperStatement extends DateOperStatement {

    }

    public static class MonthDateOperStatement extends DateOperStatement {

    }

    public static class WeekDateOperStatement extends DateOperStatement {

    }

    public static class DayDateOperStatement extends DateOperStatement {

    }

    public static class HourDateOperStatement extends DateOperStatement {

    }

    public static class MinuteDateOperStatement extends DateOperStatement {

    }

    public static class SecondDateOperStatement extends DateOperStatement {

    }


}
