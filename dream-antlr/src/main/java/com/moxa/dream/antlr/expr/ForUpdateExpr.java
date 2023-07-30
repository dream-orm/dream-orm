package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.ForUpdateNoWaitStatement;
import com.moxa.dream.antlr.smt.ForUpdateStatement;
import com.moxa.dream.antlr.smt.Statement;

/**
 * for update语法解析器
 */
public class ForUpdateExpr extends SqlExpr {
    ForUpdateStatement forUpdateStatement = new ForUpdateStatement();

    public ForUpdateExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.FOR);
    }

    @Override
    protected Statement exprFor(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.UPDATE);
        return expr();
    }

    @Override
    protected Statement exprUpdate(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.NOWAIT, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprNoWait(ExprInfo exprInfo) throws AntlrException {
        forUpdateStatement = new ForUpdateNoWaitStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return forUpdateStatement;
    }
}
