package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.Constant;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;

public class ColumnExpr extends SqlExpr {
    private Statement statement;

    public ColumnExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(Constant.FUNCTION).addExprTypes(Constant.SYMBOL).addExprTypes(ExprType.STAR, ExprType.CASE, ExprType.LBRACE, ExprType.INVOKER);
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) {
        FunctionExpr functionExpr = new FunctionExpr(exprReader);
        statement = functionExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStar(ExprInfo exprInfo) {
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        statement = symbolExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSymbol(ExprInfo exprInfo) {
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        statement = symbolExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCase(ExprInfo exprInfo) {
        CaseExpr caseExpr = new CaseExpr(exprReader);
        statement = caseExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) {
        BraceExpr braceExpr = new BraceExpr(exprReader);
        statement = braceExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInvoker(ExprInfo exprInfo) {
        InvokerExpr invokerExpr = new InvokerExpr(exprReader);
        statement = invokerExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }


    @Override
    public Statement nil() {
        return statement;
    }

}
