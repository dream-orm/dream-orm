package com.moxa.dream.antlr.smt;

public class ConditionStatement extends Statement {
    private Statement left;
    private OperStatement oper;
    private Statement right;

    public Statement getLeft() {
        return left;
    }

    public void setLeft(Statement left) {
        this.left = left;
        if (left != null) {
            left.parentStatement = this;
        }
    }

    public OperStatement getOper() {
        return oper;
    }

    public void setOper(OperStatement oper) {
        this.oper = oper;
        if (oper != null) {
            oper.parentStatement = this;
        }
    }

    public Statement getRight() {
        return right;
    }

    public void setRight(Statement right) {
        this.right = right;
        if (right != null) {
            right.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(left, right);
    }
}
