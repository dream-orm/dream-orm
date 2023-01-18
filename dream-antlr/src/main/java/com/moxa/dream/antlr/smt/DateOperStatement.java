package com.moxa.dream.antlr.smt;

public abstract class DateOperStatement extends Statement {
    private Statement date;
    private Statement qty;

    public Statement getDate() {
        return date;
    }

    public void setDate(Statement date) {
        this.date = date;
        if (date != null) {
            date.parentStatement = this;
        }
    }

    public Statement getQty() {
        return qty;
    }

    public void setQty(Statement qty) {
        this.qty = qty;
        if (qty != null) {
            qty.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(date, qty);
    }

    public static class YearDateAddStatement extends DateOperStatement {

    }

    public static class YearDateSubStatement extends DateOperStatement {

    }

    public static class QuarterDateAddStatement extends DateOperStatement {

    }

    public static class QuarterDateSubStatement extends DateOperStatement {

    }

    public static class MonthDateAddStatement extends DateOperStatement {

    }

    public static class MonthDateSubStatement extends DateOperStatement {

    }

    public static class WeekDateAddStatement extends DateOperStatement {

    }

    public static class WeekDateSubStatement extends DateOperStatement {

    }

    public static class DayDateAddStatement extends DateOperStatement {

    }

    public static class DayDateSubStatement extends DateOperStatement {

    }

    public static class HourDateAddStatement extends DateOperStatement {

    }

    public static class HourDateSubStatement extends DateOperStatement {

    }

    public static class MinuteDateAddStatement extends DateOperStatement {

    }

    public static class MinuteDateSubStatement extends DateOperStatement {

    }

    public static class SecondDateAddStatement extends DateOperStatement {

    }

    public static class SecondDateSubStatement extends DateOperStatement {

    }
}
