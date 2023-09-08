package com.dream.antlr.smt;

public abstract class JoinStatement extends Statement {
    private Statement joinTable;
    private Statement on;

    public Statement getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(Statement joinTable) {
        this.joinTable = wrapParent(joinTable);
    }

    public Statement getOn() {
        return on;
    }

    public void setOn(Statement on) {
        this.on = wrapParent(on);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(joinTable, on);
    }

    @Override
    public JoinStatement clone() {
        JoinStatement joinStatement = (JoinStatement) super.clone();
        joinStatement.setJoinTable(clone(joinTable));
        joinStatement.setOn(clone(on));
        return joinStatement;
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
