package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.Constant;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;

public class InvokerExpr extends SqlExpr {

    private final InvokerStatement invokerStatement = new InvokerStatement();

    public InvokerExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.INVOKER);
    }

    @Override
    protected Statement exprInvoker(ExprInfo exprInfo) {
        push();
        setExprTypes(Constant.FUNCTION).addExprTypes(Constant.KEYWORD).addExprTypes(ExprType.LETTER);
        return expr();
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprKeyWord(ExprInfo exprInfo) {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) {
        return exprName(exprInfo);
    }

    private Statement exprName(ExprInfo exprInfo) {
        push();
        if (invokerStatement.getFunction() == null) {
            invokerStatement.setFunction(exprInfo.getInfo());
            setExprTypes(ExprType.COLON, ExprType.LBRACE);
        } else {
            invokerStatement.setNamespace(exprInfo.getInfo());
            setExprTypes(ExprType.LBRACE);
        }
        return expr();
    }

    @Override
    protected Statement exprColon(ExprInfo exprInfo) {
        push();
        setExprTypes(Constant.FUNCTION).addExprTypes(Constant.KEYWORD).addExprTypes(ExprType.LETTER);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ","));
        invokerStatement.setParamStatement(listColumnExpr.expr());
        setExprTypes(ExprType.RBRACE);
        return expr();
    }

    @Override
    protected Statement exprRBrace(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return invokerStatement;
    }
}
