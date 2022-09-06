package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.LimitStatement;
import com.moxa.dream.antlr.smt.Statement;

public class LimitExpr extends HelperExpr {
    private final LimitStatement limitStatement = new LimitStatement();

    public LimitExpr(ExprReader exprReader) {
        this(exprReader, () -> new CompareExpr(exprReader));
    }

    public LimitExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.LIMIT, ExprType.OFFSET);
    }


    @Override
    protected Statement exprLimit(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprComma(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    public Statement nil() {
        return limitStatement;
    }

    @Override
    protected Statement exprOffSet(ExprInfo exprInfo) {
        push();
        limitStatement.setOffset(true);
        Statement first;
        if ((first = limitStatement.getFirst()) != null) {
            limitStatement.setSecond(first);
            limitStatement.setFirst(null);
        }
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) {
        if (limitStatement.isOffset()) {
            if (limitStatement.getFirst() == null) {
                limitStatement.setFirst(statement);
                setExprTypes(ExprType.LIMIT, ExprType.NIL);
            } else {
                limitStatement.setSecond(statement);
                setExprTypes(ExprType.NIL);
            }
        } else {
            if (limitStatement.getFirst() == null) {
                limitStatement.setFirst(statement);
                setExprTypes(ExprType.OFFSET, ExprType.COMMA, ExprType.NIL);
            } else {
                limitStatement.setSecond(statement);
                setExprTypes(ExprType.NIL);
            }
        }
        return expr();
    }
}
