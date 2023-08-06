package com.dream.antlr.smt;

public class ConditionStatement extends Statement {
    private Statement left;
    private OperStatement oper;
    private Statement right;

    public Statement getLeft() {
        return left;
    }

    public void setLeft(Statement left) {
        this.left = wrapParent(left);
    }

    public OperStatement getOper() {
        return oper;
    }

    public void setOper(OperStatement oper) {
        this.oper = wrapParent(oper);
    }

    public Statement getRight() {
        return right;
    }

    public void setRight(Statement right) {
        this.right = wrapParent(right);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(left, right);
    }
}
