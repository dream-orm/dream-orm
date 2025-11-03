package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.ReplaceIntoStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

/**
 * replace into语法解析器
 */
public class ReplaceIntoExpr extends HelperExpr {
    private final ReplaceIntoStatement replaceIntoStatement = new ReplaceIntoStatement();

    public ReplaceIntoExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        this(exprReader, () -> new SymbolExpr(exprReader, myFunctionFactory), myFunctionFactory);
    }

    public ReplaceIntoExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
        super(exprReader, helper, myFunctionFactory);
        setExprTypes(ExprType.REPLACE);
    }

    @Override
    protected Statement exprReplace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.INTO);
        return expr();
    }

    @Override
    protected Statement exprInto(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.MY_FUNCTION, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        BraceExpr braceExpr = new BraceExpr(exprReader, myFunctionFactory);
        Statement statement = braceExpr.expr();
        replaceIntoStatement.setColumns(statement);
        setExprTypes(ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    @Override
    protected Statement exprValues(ExprInfo exprInfo) throws AntlrException {
        InsertExpr.ValuesExpr valuesExpr = new InsertExpr.ValuesExpr(exprReader, myFunctionFactory);
        replaceIntoStatement.setValues(valuesExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        QueryExpr queryExpr = new QueryExpr(exprReader, myFunctionFactory);
        Statement statement = queryExpr.expr();
        replaceIntoStatement.setValues(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return replaceIntoStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        replaceIntoStatement.setTable(statement);
        setExprTypes(ExprType.LBRACE, ExprType.VALUES, ExprType.SELECT);
        return expr();
    }

    @Override
    protected Statement exprMyFunction(ExprInfo exprInfo) throws AntlrException {
        push();
        replaceIntoStatement.setTable(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.LBRACE);
        return expr();
    }
}
