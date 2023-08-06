package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.LimitStatement;
import com.dream.antlr.smt.Statement;

/**
 * 分页语法解析器
 */
public class LimitExpr extends HelperExpr {
    private final LimitStatement limitStatement = new LimitStatement();

    public LimitExpr(ExprReader exprReader) {
        this(exprReader, () -> new CompareExpr(exprReader));
    }

    public LimitExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.LIMIT);
    }


    @Override
    protected Statement exprLimit(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprComma(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    public Statement nil() {
        return limitStatement;
    }

    @Override
    protected Statement exprOffSet(ExprInfo exprInfo) throws AntlrException {
        push();
        limitStatement.setOffset(true);
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        if (limitStatement.getFirst() == null) {
            limitStatement.setFirst(statement);
            setExprTypes(ExprType.OFFSET, ExprType.COMMA, ExprType.NIL);
        } else {
            limitStatement.setSecond(statement);
            setExprTypes(ExprType.NIL);
        }
        return expr();
    }
}
