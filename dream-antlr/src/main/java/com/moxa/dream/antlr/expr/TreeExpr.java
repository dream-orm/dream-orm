package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.ConditionStatement;
import com.moxa.dream.antlr.smt.OperStatement;
import com.moxa.dream.antlr.smt.Statement;

public abstract class TreeExpr extends HelperExpr {
    protected ConditionStatement cur = new ConditionStatement();
    protected ConditionStatement top = cur;

    public TreeExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
    }

    protected void exprTree(OperStatement oper) {
        if (cur.getOper() == null) {
            cur.setOper(oper);
        } else {
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setOper(oper);
            while (true) {
                if (cur == null) {
                    conditionStatement.setLeft(top);
                    cur = top = conditionStatement;
                    break;
                } else if (oper.getLevel() - cur.getOper().getLevel() >= 0) {
                    conditionStatement.setLeft(cur.getRight());
                    cur.setRight(conditionStatement);
                    cur = conditionStatement;
                    break;
                } else {
                    Statement parentStatement = cur.getParentStatement();
                    if (parentStatement instanceof ConditionStatement) {
                        cur = (ConditionStatement) parentStatement;
                    } else {
                        cur = null;
                    }
                }
            }
        }
    }

    protected void exprTree(Statement statement) {
        OperStatement oper = cur.getOper();
        if (oper == null) {
            cur.setLeft(statement);
        } else {
            cur.setRight(statement);
        }
    }
}
