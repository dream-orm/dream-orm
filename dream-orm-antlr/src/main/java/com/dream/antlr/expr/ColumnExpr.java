package com.dream.antlr.expr;

import com.dream.antlr.config.Constant;
import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

/**
 * 字段语法解析器
 */
public class ColumnExpr extends HelperExpr {
    private Statement statement;

    public ColumnExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public ColumnExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(Constant.FUNCTION).addExprTypes(ExprType.CASE, ExprType.LBRACE, ExprType.COLON, ExprType.DOLLAR, ExprType.SHARP, ExprType.INVOKER, ExprType.INTERVAL, ExprType.HELP);
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) throws AntlrException {
        FunctionExpr functionExpr = new FunctionExpr(exprReader);
        statement = functionExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }


    @Override
    protected Statement exprCase(ExprInfo exprInfo) throws AntlrException {
        CaseExpr caseExpr = new CaseExpr(exprReader);
        statement = caseExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        BraceExpr braceExpr = new BraceExpr(exprReader);
        statement = braceExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInvoker(ExprInfo exprInfo) throws AntlrException {
        InvokerExpr invokerExpr = new InvokerExpr(exprReader);
        statement = invokerExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInterval(ExprInfo exprInfo) throws AntlrException {
        IntervalExpr intervalExpr = new IntervalExpr(exprReader);
        statement = intervalExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprColon(ExprInfo exprInfo) throws AntlrException {
        return exprEmit(exprInfo);
    }

    @Override
    protected Statement exprDollar(ExprInfo exprInfo) throws AntlrException {
        return exprEmit(exprInfo);
    }

    @Override
    protected Statement exprSharp(ExprInfo exprInfo) throws AntlrException {
        return exprEmit(exprInfo);
    }

    protected Statement exprEmit(ExprInfo exprInfo) throws AntlrException {
        EmitExpr emitExpr = new EmitExpr(exprReader);
        this.statement = emitExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return statement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        this.statement = statement;
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
