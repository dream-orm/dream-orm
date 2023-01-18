package com.moxa.dream.antlr.smt;

public abstract class JoinStatement extends Statement {
    private Statement joinTable;
    private Statement on;

    public Statement getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(Statement joinTable) {
        this.joinTable = joinTable;
        if (joinTable != null) {
            joinTable.parentStatement = this;
        }
    }

    public Statement getOn() {
        return on;
    }

    public void setOn(Statement on) {
        this.on = on;
        if (on != null) {
            on.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(joinTable, on);
    }

    public static class LeftJoinStatement extends JoinStatement {

    }

    public static class RightJoinStatement extends JoinStatement {

    }

    public static class InnerJoinStatement extends JoinStatement {

    }

    public static class CrossJoinStatement extends JoinStatement {

    }


}
