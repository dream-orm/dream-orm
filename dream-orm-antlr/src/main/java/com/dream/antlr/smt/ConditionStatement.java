package com.dream.antlr.smt;

public class ConditionStatement extends Statement {
    protected ConditionStatement parentStatement;
    private Statement left;
    private OperStatement oper;
    private Statement right;

    public Statement getLeft() {
        return left;
    }

    public void setLeft(Statement left) {
        if (left instanceof ConditionStatement) {
            ((ConditionStatement) left).parentStatement = this;
        }
        this.left = left;
    }

    public OperStatement getOper() {
        return oper;
    }

    public void setOper(OperStatement oper) {
        this.oper = oper;
    }

    public Statement getRight() {
        return right;
    }

    public void setRight(Statement right) {
        if (right instanceof ConditionStatement) {
            ((ConditionStatement) right).parentStatement = this;
        }
        this.right = right;
    }

    public ConditionStatement getParentStatement() {
        return parentStatement;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(left, right);
    }

    @Override
    public ConditionStatement clone() {
        ConditionStatement conditionStatement = (ConditionStatement) super.clone();
        conditionStatement.setLeft(clone(left));
        conditionStatement.setOper((OperStatement) clone(oper));
        conditionStatement.setRight(clone(right));
        return conditionStatement;
    }
}
