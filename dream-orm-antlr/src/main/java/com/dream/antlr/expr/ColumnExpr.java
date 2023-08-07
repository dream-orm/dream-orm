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
public class ColumnExpr extends SqlExpr {
    private Statement statement;

    public ColumnExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(Constant.FUNCTION).addExprTypes(Constant.SYMBOL).addExprTypes(ExprType.STAR, ExprType.CASE, ExprType.LBRACE, ExprType.INVOKER);
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) throws AntlrException {
        FunctionExpr functionExpr = new FunctionExpr(exprReader);
        statement = functionExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStar(ExprInfo exprInfo) throws AntlrException {
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        statement = symbolExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSymbol(ExprInfo exprInfo) throws AntlrException {
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        statement = symbolExpr.expr();
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
    public Statement nil() {
        return statement;
    }

}
